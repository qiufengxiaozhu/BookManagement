package com.java.book.vo;

import com.java.book.entity.Book;
import com.java.book.entity.Borrow;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 封装 借阅 查询条件
 * @author 邱高强
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class BorrowVo extends Borrow {

    private static final long serialVersionUID = 1L;

    /**
     * 第几页
     */
    private Integer pageNum = 1;

    /**
     * 页面大小
     */
    private Integer pageSize = 10;

    /**
     * 接收多个ID,一般用于批量删除
     */
    private Integer[] ids;


    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date startTime;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date endTime;
}
