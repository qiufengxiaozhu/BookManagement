package com.java.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Major;
import com.java.book.service.MajorService;
import com.java.book.service.MajorService;
import com.java.book.vo.MajorVo;
import com.java.book.vo.MajorVo;
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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 专业 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
@Api(tags = "专业管理")
@RestController
@RequestMapping("major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @ApiOperation(value = "分页查询信息")
    @GetMapping("getPageMajor")
    public ResultObj getPageMajor(MajorVo majorVo) {

        // 封装page对象
        IPage<Major> page = new Page<>(majorVo.getPageNum(), majorVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(majorVo.getMajor()), "major", majorVo.getMajor());
        // 等于
        queryWrapper.eq("flag", Constant.DELETE_NOT);
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        majorService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation( value = "批量删除专业信息" )
    @PostMapping( "batchDeleteMajor" )
    public ResultObj batchDeleteMajor(MajorVo majorVo) {

        try {

            // 获取所有选中的id值
            List<Integer> idList = new ArrayList<>(Arrays.asList(majorVo.getIds()));

            majorService.removeByIds(idList);
            return ResultObj.ok().msg(Constant.BATCH_DEL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BATCH_DEL_FAILURE);
        }

    }

    @ApiOperation( value = "删除单个专业信息" )
    @PostMapping( "deleteMajor" )
    public ResultObj deleteMajor(MajorVo majorVo) {

        try {

            // 获取选中的id值
            Integer id = majorVo.getId();
            // 逻辑删除
            majorVo.setFlag(Constant.DELETE_YES);
            // 删除时间
            majorVo.setUpdateTime(new Date());

            majorService.updateById(majorVo);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "新增专业信息" )
    @PostMapping( "addMajor" )
    public ResultObj addMajor(MajorVo majorVo) {

        try {

            if ( StringUtils.isEmpty(majorVo.getMajor())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            majorVo.setCreateTime(new Date());
            majorVo.setUpdateTime(new Date());
            majorVo.setFlag(0);

            majorService.save(majorVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "修改专业信息" )
    @PostMapping( "updateMajor" )
    public ResultObj updateMajor(MajorVo majorVo) {

        try {

            if ( StringUtils.isEmpty(majorVo.getMajor())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改公告时间
            majorVo.setUpdateTime(new Date());

            majorService.updateById(majorVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }
}

