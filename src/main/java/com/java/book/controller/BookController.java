package com.java.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Book;
import com.java.book.entity.Borrow;
import com.java.book.service.BookService;
import com.java.book.service.BorrowService;
import com.java.book.vo.BookVo;
import com.java.book.vo.PublisherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 书籍表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api(tags = "图书管理")
@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @ApiOperation(value = "分页查询图书")
    @GetMapping("getPageBook")
    public ResultObj getPageBook(BookVo bookVo) {

        // 封装page对象
        IPage<Book> page = new Page<>(bookVo.getPageNum(), bookVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(bookVo.getBooknum()), "booknum", bookVo.getBooknum());
        queryWrapper.like(!StringUtils.isEmpty(bookVo.getName()), "name", bookVo.getName());
        queryWrapper.like(!StringUtils.isEmpty(bookVo.getAuthor()), "author", bookVo.getAuthor());
        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(bookVo.getFkPublisherid()), "fk_publisherId", bookVo.getFkPublisherid());
        queryWrapper.eq(!StringUtils.isEmpty(bookVo.getFkCategoryid()), "fk_categoryId", bookVo.getFkCategoryid());
        queryWrapper.eq(!StringUtils.isEmpty(bookVo.getPutaway()), "putaway", bookVo.getPutaway());
        queryWrapper.eq("flag", Constant.DELETE_NOT);
        // 大于
        queryWrapper.gt("number", 0);
        // 大于等于
        queryWrapper.ge(!StringUtils.isEmpty(bookVo.getStartTime()), "publish_time", bookVo.getStartTime());
        // 小于等于
        queryWrapper.le(!StringUtils.isEmpty(bookVo.getEndTime()), "publish_time", bookVo.getEndTime());
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        bookService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).pages(page.getPages()).list( page.getRecords());
    }

    @ApiOperation(value = "分页查询图书-最受欢迎图书排行榜")
    @GetMapping("getPageBookByPopular")
    public ResultObj getPageBookByPopular(BookVo bookVo) {

        // 封装page对象
        IPage<Borrow> page = new Page<>(bookVo.getPageNum(), bookVo.getPageSize());
        // 在borrow表中count(booknum),按合计进行排序分组
        // 封装查询条件
        QueryWrapper<Borrow> queryWrapper1 = new QueryWrapper<>();

        queryWrapper1.select("booknum", "count(booknum)");
        queryWrapper1.groupBy("booknum");
        queryWrapper1.orderByDesc("count(booknum)");

        borrowService.page(page, queryWrapper1);

        // 根据分页数据的booknum,读取book表中的数据
        List<Book> bookList = new ArrayList<>();
        for ( Borrow borrow : page.getRecords()) {
            // 封装查询条件
            QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("booknum", borrow.getBooknum());
            bookList.add(bookService.getOne(queryWrapper));
        }

        return ResultObj.ok().total(page.getTotal()).pages(page.getPages()).list( bookList);
//        return ResultObj.ok().total(page.getTotal()).pages(page.getPages()).list( page.getRecords());
//        return ResultObj.ok();
    }

    @ApiOperation( value = "新增图书" )
    @PostMapping( "addBook" )
    public ResultObj addBook(BookVo bookVo) {

        try {

            if ( StringUtils.isEmpty(bookVo.getBooknum()+"")) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            bookVo.setCreateTime(new Date());
            bookVo.setUpdateTime(new Date());
            bookVo.setFlag(0);
            bookVo.setPutaway(0);

            bookService.save(bookVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "上/下架图书" )
    @PostMapping( "putawayBook" )
    public ResultObj putawayBook(BookVo bookVo) {

        try {

            if ( StringUtils.isEmpty(bookVo.getId())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改图书时间
            bookVo.setUpdateTime(new Date());
            bookVo.setPutaway(bookVo.getPutaway() > 0 ? 0 : 1);

            bookService.updateById(bookVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }

    @ApiOperation( value = "修改图书" )
    @PostMapping( "updateBook" )
    public ResultObj updateBook(BookVo bookVo) {

        try {

            if ( StringUtils.isEmpty(bookVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改图书时间
            bookVo.setUpdateTime(new Date());

            bookService.updateById(bookVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }

    @ApiOperation( value = "删除单个图书" )
    @PostMapping( "deleteBook" )
    public ResultObj deleteBook(BookVo bookVo) {

        try {

            // 获取选中的id值
            Integer id = bookVo.getId();
            // 逻辑删除
            bookVo.setFlag(Constant.DELETE_YES);
            // 删除时间
            bookVo.setUpdateTime(new Date());

            bookService.updateById(bookVo);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "查询单个图书" )
    @GetMapping( "getBook" )
    public ResultObj getBook(BookVo bookVo) {

        try {

            // 封装查询条件
            QueryWrapper<Book> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq(!StringUtils.isEmpty(bookVo.getId()), "id", bookVo.getId());
            queryWrapper.eq(!StringUtils.isEmpty(bookVo.getBooknum()), "booknum", bookVo.getBooknum());

            Book book = bookService.getOne(queryWrapper);
            return ResultObj.ok().data("book", book).msg(Constant.SELECT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.SELECT_FAILURE);
        }

    }

}

