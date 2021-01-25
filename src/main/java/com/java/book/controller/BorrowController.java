package com.java.book.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Book;
import com.java.book.entity.Borrow;
import com.java.book.entity.Ticket;
import com.java.book.entity.User;
import com.java.book.service.BookService;
import com.java.book.service.BorrowService;
import com.java.book.service.TicketService;
import com.java.book.util.DateUtils;
import com.java.book.util.WebUtils;
import com.java.book.vo.BookVo;
import com.java.book.vo.BorrowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 借书还书表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
@Api( tags = "借阅管理" )
@RestController
@RequestMapping( "borrow" )
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TicketService ticketService;

    @ApiOperation( value = "分页查询借阅记录" )
    @GetMapping( "getPageBorrow" )
    public ResultObj getPageBorrow(BorrowVo borrowVo) {

        // 封装page对象
        IPage<Borrow> page = new Page<>(borrowVo.getPageNum(), borrowVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(borrowVo.getBooknum()), "booknum", borrowVo.getBooknum());
        queryWrapper.like(!StringUtils.isEmpty(borrowVo.getLoginname()), "loginname", borrowVo.getLoginname());
        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(borrowVo.getFlag()), "flag", borrowVo.getFlag());
        // 大于等于
        queryWrapper.ge(!StringUtils.isEmpty(borrowVo.getStartTime()), "borrow_time", borrowVo.getStartTime());
        // 小于等于
        queryWrapper.le(!StringUtils.isEmpty(borrowVo.getEndTime()), "borrow_time", borrowVo.getEndTime());
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        borrowService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).pages(page.getPages()).list(page.getRecords());
    }

    @ApiOperation( value = "根据用户与序列号查询未归还的借阅记录" )
    @GetMapping( "getBorrowRecords" )
    public ResultObj getBorrowRecords(BorrowVo borrowVo) {

        if ( StringUtils.isEmpty(borrowVo.getBooknum() + "") || StringUtils.isEmpty(borrowVo.getLoginname()) ) {
            return ResultObj.error().msg(Constant.EXCEPTION);
        }

        // 封装查询条件
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();

        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(borrowVo.getLoginname()), "loginname", borrowVo.getLoginname());

        // 归还记录为空
        queryWrapper.isNull("return_time");

        // 根据用户名查询该用户未归还的借阅记录有几条
        List<Borrow> list = borrowService.list(queryWrapper);

        if ( list.size() >= Constant.BORROW_MAX_NUMBER ) {
            return ResultObj.ok().msg(Constant.BORROW_NUMBER);
        }

        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(borrowVo.getBooknum()), "booknum", borrowVo.getBooknum());

        // 查询
        Borrow borrow = borrowService.getOne(queryWrapper);
        if ( !StringUtils.isEmpty(borrow) ) {
            return ResultObj.ok().msg(Constant.BORROW_RECORDS);
        } else {
            return ResultObj.error();
        }
    }

    @ApiOperation( value = "根据用户查询所有借阅记录" )
    @GetMapping( "getPageBorrowAllRecords" )
    public ResultObj getBorrowAllRecords(BorrowVo borrowVo) {

        // 从 session 中获取用户信息
        User user = (User) WebUtils.getSession().getAttribute("user");
        if ( Constant.USER_TYPE_NORMAL.equals(user.getType()) ) {
            borrowVo.setLoginname(user.getLoginname());
        }

        // 封装page对象
        IPage<Borrow> page = new Page<>(borrowVo.getPageNum(), borrowVo.getPageSize());
        // 封装查询条件
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(borrowVo.getBooknum()), "booknum", borrowVo.getBooknum());
        // 等于  如果是管理员，可以查询所有人员数据
        queryWrapper.eq(Constant.USER_TYPE_NORMAL.equals(user.getType()) || !StringUtils.isEmpty(borrowVo.getLoginname()), "loginname", borrowVo.getLoginname());
        queryWrapper.eq(!StringUtils.isEmpty(borrowVo.getFlag()), "flag", borrowVo.getFlag());
        queryWrapper.eq(!StringUtils.isEmpty(borrowVo.getFkTicketid()), "fk_ticketId", borrowVo.getFkTicketid());
        // 大于等于
        queryWrapper.ge(!StringUtils.isEmpty(borrowVo.getStartTime()), "borrow_time", borrowVo.getStartTime());
        // 小于等于
        queryWrapper.le(!StringUtils.isEmpty(borrowVo.getEndTime()), "borrow_time", borrowVo.getEndTime());
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        borrowService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).pages(page.getPages()).list(page.getRecords());

    }

    @ApiOperation( value = "根据借阅记录的id查看该记录是否已经归还" )
    @GetMapping( "getBorrowFlagById" )
    public ResultObj getBorrowFlagById(BorrowVo borrowVo) {

        if ( StringUtils.isEmpty(borrowVo.getId()) ) {
            return ResultObj.error().msg(Constant.EXCEPTION);
        }

        Borrow borrow = borrowService.getById(borrowVo.getId());

        if ( StringUtils.isEmpty(borrow.getReturnTime()) ) {
            return ResultObj.error().msg(Constant.TICKET_CAN_NOT_PAY);
        }

        return ResultObj.ok();
    }

    @ApiOperation( value = "新增借阅记录" )
    @PostMapping( "addBorrow" )
    public ResultObj addBorrow(BorrowVo borrowVo) {

        try {

            if ( StringUtils.isEmpty(borrowVo.getBooknum()) || StringUtils.isEmpty(borrowVo.getExpectTime()) ) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 从 session 中获取用户信息
            User user = (User) WebUtils.getSession().getAttribute("user");

            borrowVo.setLoginname(user.getLoginname());
            borrowVo.setBorrowTime(new Date());
            borrowVo.setCreateTime(new Date());
            borrowVo.setUpdateTime(new Date());
            borrowVo.setFlag(0);

            boolean save = borrowService.save(borrowVo);

            if ( save ) {

                // 封装查询条件
                QueryWrapper<Book> queryWrapper = new QueryWrapper<>();

                queryWrapper.eq("booknum", borrowVo.getBooknum());

                // 从库存里减一
                Book book = bookService.getOne(queryWrapper);
                book.setNumber(book.getNumber() - 1);

                boolean update = bookService.updateById(book);

                System.out.println(update ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE);
            }

            return ResultObj.ok().msg(Constant.BORROW_SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BORROW_FAILURE);
        }
    }

    @ApiOperation( value = "归档借阅记录" )
    @PostMapping( "returnBorrow" )
    public ResultObj returnBorrow(BorrowVo borrowVo) {

        try {

            if ( StringUtils.isEmpty(borrowVo.getBooknum() + "") ) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 判断是否逾期  逾期时间小于今天，归还的时候则为已逾期
            if ( DateUtils.compare(borrowVo.getExpectTime(), DateUtil.parse(DateUtil.today() + " 23:59:59")) ) {
               //  已逾期
                borrowVo.setFlag(1);
            } else {
                borrowVo.setFlag(0);
            }

            // 设置归档时间
            borrowVo.setReturnTime(new Date());

            // 修改更新时间
            borrowVo.setUpdateTime(new Date());

            boolean update = borrowService.updateById(borrowVo);

            if ( update ) {

                // 该书籍归档了一本，库存量加一
                // 封装查询条件
                QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("booknum", borrowVo.getBooknum());
                // 从库存里减一
                Book book = bookService.getOne(queryWrapper);
                book.setNumber(book.getNumber() + 1);

                update = bookService.updateById(book);

                // 将罚单表的是否归档更新为已归档
                QueryWrapper<Ticket> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", borrowVo.getFkTicketid());
                // 已归档
                Ticket ticket = ticketService.getOne(queryWrapper2);
                if ( !StringUtils.isEmpty(ticket) && update ) {
                    ticket.setReturnFlag(1);
                    update = ticketService.updateById(ticket);
                }

                System.out.println(update ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE);
            }

            return ResultObj.ok().msg(Constant.BOOK_RETURN_SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BOOK_RETURN_FAILURE);
        }
    }

    @ApiOperation( value = "撤销归档借阅记录" )
    @PostMapping( "finishBorrow" )
    public ResultObj finishBorrow(BorrowVo borrowVo) {

        try {

            if ( StringUtils.isEmpty(borrowVo.getBooknum() + "") ) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 判断是否逾期  预期时间大于今天，归还的时候则为未逾期
            if ( DateUtils.compare(borrowVo.getExpectTime(), DateUtil.parse(DateUtil.today() + " 23:59:59")) ) {
               //  已逾期
                borrowVo.setFlag(1);
            } else {
                borrowVo.setFlag(0);
            }

            // 设置归档时间
            // mybatis-plus会将所有为空的字段在修改时进行过滤，不进行设为空的修改操作。
            // 解决办法：在相关字段上加上注解 @TableField(strategy = FieldStrategy.IGNORED)
            borrowVo.setReturnTime(null);

            // 修改更新时间
            borrowVo.setUpdateTime(new Date());

            boolean update = borrowService.updateById(borrowVo);
            if ( update ) {

                // 1、该书籍撤销归档了一本，库存量减一
                // 封装查询条件
                QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("booknum", borrowVo.getBooknum());
                // 从库存里减一
                Book book = bookService.getOne(queryWrapper);
                book.setNumber(book.getNumber() - 1);

                update = bookService.updateById(book);

                // 2、将罚单表的是否归档更新为未归档
                QueryWrapper<Ticket> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", borrowVo.getFkTicketid());
                // 已归档
                Ticket ticket = ticketService.getOne(queryWrapper2);
                if ( !StringUtils.isEmpty(ticket) && update ) {
                    ticket.setReturnFlag(0);
                    update = ticketService.updateById(ticket);
                }

                System.out.println(update ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE);
            }

            return ResultObj.ok().msg(Constant.BOOK_FINISH_SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BOOK_FINISH_FAILURE);
        }
    }

    @ApiOperation( value = "删除单个借阅记录" )
    @PostMapping( "deleteBorrow" )
    public ResultObj deleteBorrow(BorrowVo borrowVo) {

        try {

            // 获取选中的id值
            Integer id = borrowVo.getId();

            borrowService.removeById(id);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }


}

