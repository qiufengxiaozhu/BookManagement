package com.java.book.vo;

import com.java.book.common.Constant;
import com.java.book.entity.Book;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 封装 书籍 查询条件
 * @author 邱高强
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class BookVo extends Book {

    private static final long serialVersionUID = 1L;

    /**
     * 第几页
     */
    private Integer pageNum = 1;

    /**
     * 页面大小,书架每行只能放下4本书
     */
    private Integer pageSize = 8;

    /**
     * 接收多个ID,一般用于批量删除
     */
    private Integer[] ids;


    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date startTime;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date endTime;
}
