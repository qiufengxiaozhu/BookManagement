package com.java.book.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.*;
import com.java.book.entity.Ticket;
import com.java.book.service.BookService;
import com.java.book.service.BorrowService;
import com.java.book.service.TicketService;
import com.java.book.util.WebUtils;
import com.java.book.vo.TicketVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 罚单信息表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api(tags = "罚单管理")
@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @ApiOperation(value = "分页查询罚单记录")
    @GetMapping("getPageTicket")
    public ResultObj getPageTicket(TicketVo ticketVo) {

        // 从 session 中获取用户信息
        User user = (User) WebUtils.getSession().getAttribute("user");
        if ( Constant.USER_TYPE_NORMAL.equals(user.getType()) ) {
            ticketVo.setLoginname(user.getLoginname());
        }

        // 封装page对象
        IPage<Ticket> page = new Page<>(ticketVo.getPageNum(), ticketVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(ticketVo.getBooknum()), "booknum", ticketVo.getBooknum());
        // 等于
        // 等于  如果是管理员，可以查询所有人员数据
        queryWrapper.eq(Constant.USER_TYPE_NORMAL.equals(user.getType()) || !StringUtils.isEmpty(ticketVo.getLoginname()), "loginname", ticketVo.getLoginname());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getReturnFlag()), "return_flag", ticketVo.getReturnFlag());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getPayFlag()), "pay_flag", ticketVo.getPayFlag());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getId()), "id", ticketVo.getId());

        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        ticketService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation(value = "分页查询超时记录")
    @GetMapping("getPageOverTime")
    public ResultObj getPageOverTime(TicketVo ticketVo) {

        // 从 session 中获取用户信息
        User user = (User) WebUtils.getSession().getAttribute("user");
        ticketVo.setLoginname(user.getLoginname());

        // 封装page对象
        IPage<Ticket> page = new Page<>(ticketVo.getPageNum(), ticketVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(ticketVo.getBooknum()), "booknum", ticketVo.getBooknum());
        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getLoginname()), "loginname", ticketVo.getLoginname());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getReturnFlag()), "return_flag", ticketVo.getReturnFlag());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getPayFlag()), "pay_flag", ticketVo.getPayFlag());

        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        ticketService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation(value = "分页查询罚金记录")
    @GetMapping("getPageOverFee")
    public ResultObj getPageOverFee(TicketVo ticketVo) {

        // 从 session 中获取用户信息
        User user = (User) WebUtils.getSession().getAttribute("user");
        ticketVo.setLoginname(user.getLoginname());

        // 封装page对象
        IPage<Ticket> page = new Page<>(ticketVo.getPageNum(), ticketVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(ticketVo.getBooknum()), "booknum", ticketVo.getBooknum());
        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getLoginname()), "loginname", ticketVo.getLoginname());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getReturnFlag()), "return_flag", ticketVo.getReturnFlag());
        queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getPayFlag()), "pay_flag", ticketVo.getPayFlag());

        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        ticketService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation( value = "新增罚单记录" )
    @PostMapping( "addTicket" )
    public ResultObj addTicket(TicketVo ticketVo) {

        try {

            if ( StringUtils.isEmpty(ticketVo.getBooknum())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            ticketVo.setCreateTime(new Date());
            ticketVo.setUpdateTime(new Date());
            ticketVo.setReturnFlag(0);
            ticketVo.setPayFlag(0);
            ticketVo.setOverFee(Constant.OVER_DATE_FEE);
            ticketVo.setOverDate(0);
            ticketVo.setOverFee(BigDecimal.valueOf(0));

            ticketService.save(ticketVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "修改罚单记录" )
    @PostMapping( "updateTicket" )
    public ResultObj updateTicket(TicketVo ticketVo) {

        try {

            if ( StringUtils.isEmpty(ticketVo.getBooknum())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            if ( ticketVo.getPayFlag() == 1 ) {
                return ResultObj.error().msg(Constant.TICKET_IS_FINISH);
            }

            // 从 session 中获取用户信息
            User user = (User) WebUtils.getSession().getAttribute("user");
            ticketVo.setLoginname(user.getLoginname());

            // 获取该图书借阅记录
            QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getBooknum()), "booknum", ticketVo.getBooknum());
            queryWrapper.eq(!StringUtils.isEmpty(ticketVo.getLoginname()), "loginname", ticketVo.getLoginname());

            Borrow borrow = borrowService.getOne(queryWrapper);

            // 计算超期天数 = 实际还书时间 - 预期归还时间
            long betweenDay = DateUtil.between(borrow.getExpectTime(), borrow.getReturnTime(), DateUnit.DAY);
            int days = Math.toIntExact(betweenDay) > 0 ? Math.toIntExact(betweenDay) : 1;
            ticketVo.setOverDate(days);

            // 计算逾期扣除金额
            BigDecimal overFee = new BigDecimal(days * Double.parseDouble(String.valueOf(Constant.OVER_DATE_FEE)));
            ticketVo.setOverFee(overFee);

            // 修改时间
            ticketVo.setUpdateTime(new Date());

            ticketService.updateById(ticketVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }

    @ApiOperation( value = "支付罚金" )
    @PostMapping( "payTicket" )
    public ResultObj payTicket(TicketVo ticketVo) {

        try {

            if ( StringUtils.isEmpty(ticketVo.getBooknum())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            if ( ticketVo.getReturnFlag() == 0 ) {
                return ResultObj.error().msg(Constant.TICKET_CAN_NOT_PAY);
            }

            Ticket ticket = ticketService.getById(ticketVo.getId());
            ticket.setPayFlag(1);

            ticketService.updateById(ticket);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }

}

