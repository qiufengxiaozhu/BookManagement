package com.java.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Loginfo;
import com.java.book.entity.User;
import com.java.book.service.LoginfoService;
import com.java.book.util.WebUtils;
import com.java.book.vo.LoginfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 登录日志表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api( tags = "日志管理" )
@RestController
@RequestMapping("/loginfo")
public class LoginfoController {

    @Autowired
    private LoginfoService loginfoService;

    @ApiOperation( value = "分页查询日志信息" )
    @GetMapping( "getPageLoginfo" )
    public ResultObj getPageLoginfo(LoginfoVo loginfoVo) {

        // 从 session 中获取用户信息
        User user = (User) WebUtils.getSession().getAttribute("user");
        if ( Constant.USER_TYPE_NORMAL.equals(user.getType()) ) {
            loginfoVo.setLoginname(user.getName() + "-" + user.getLoginname());
        }

        // 封装page对象
        IPage<Loginfo> page = new Page<>(loginfoVo.getPageNum(), loginfoVo.getPageSize());

        System.out.println("页数：" + loginfoVo.getPageNum() +"; 页面大小：" + loginfoVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        // 等于
        queryWrapper.eq(Constant.USER_TYPE_NORMAL.equals(user.getType()) || !StringUtils.isEmpty(loginfoVo.getLoginname()), "loginname", loginfoVo.getLoginname());
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(loginfoVo.getLoginip()), "loginip", loginfoVo.getLoginip());
        // 大于等于
        queryWrapper.ge(!StringUtils.isEmpty(loginfoVo.getStartTime()), "logintime", loginfoVo.getStartTime());
        // 小于等于
        queryWrapper.le(!StringUtils.isEmpty(loginfoVo.getEndTime()), "logintime", loginfoVo.getEndTime());
        // 排序
        queryWrapper.orderByDesc("logintime");

        // 查询
        loginfoService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation( value = "批量删除日志信息" )
    @PostMapping( "batchDeleteLoginfo" )
    public ResultObj batchDeleteLoginfo(LoginfoVo loginfoVo) {

        try {

            // 获取所有选中的id值
            List<Integer> idList = new ArrayList<>(Arrays.asList(loginfoVo.getIds()));

            loginfoService.removeByIds(idList);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "删除单个日志信息" )
    @PostMapping( "deleteLoginfo" )
    public ResultObj deleteLoginfo(LoginfoVo loginfoVo) {

        try {

            // 获取所有选中的id值
            Integer id = loginfoVo.getId();

            loginfoService.removeById(id);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }
}

