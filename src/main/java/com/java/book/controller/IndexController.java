package com.java.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Book;
import com.java.book.entity.Borrow;
import com.java.book.entity.Loginfo;
import com.java.book.entity.User;
import com.java.book.service.BookService;
import com.java.book.service.BorrowService;
import com.java.book.service.LoginfoService;
import com.java.book.util.WebUtils;
import com.java.book.vo.BookVo;
import com.java.book.vo.BorrowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 描述: 首页控制器
 * </p>
 *
 * @author 邱高强
 * @since 2021-01-24 17:56
 */
@Api(tags = "首页管理")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private LoginfoService loginfoService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @ApiOperation( value = "查询当前用户上次登录系统时间" )
    @GetMapping( "getLastLoginTime" )
    public ResultObj getLastLoginTime() {

        try {

            // 从session中获取用户信息
            User user = (User) WebUtils.getSession().getAttribute("user");

            // 封装page对象
            IPage<Book> page = new Page<>(1, 1);

            // 封装查询条件
            QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq( "loginname", user.getName()+"-"+user.getLoginname());

            queryWrapper.orderByDesc("logintime");

            Loginfo loginfo = loginfoService.getOne(queryWrapper);
            return ResultObj.ok().data("logintime", loginfo.getLogintime()).msg(Constant.SELECT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.SELECT_FAILURE);
        }

    }

    @ApiOperation( value = "借阅前十排行榜" )
    @GetMapping( "getTopTenRank" )
    public ResultObj getTopTenRank() {

        // 查询出借阅前十排行
        List<Borrow> borrowList = borrowService.getTopTenRank();

        return ResultObj.ok().list(borrowList);
    }

    @ApiOperation( value = "图书分类比例" )
    @GetMapping( "getCategoryRatio" )
    public ResultObj getCategoryRatio() {

        // 查询出图书分类比例
        List<Book> boookList = bookService.getCategoryRatio();

        return ResultObj.ok().list(boookList);
    }
}
