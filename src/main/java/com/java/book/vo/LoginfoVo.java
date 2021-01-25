package com.java.book.vo;

import com.java.book.common.Constant;
import com.java.book.entity.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 封装 日志 查询条件
 * @author 邱高强
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class LoginfoVo extends Loginfo {

    private static final long serialVersionUID = 1L;

    /**
     * 第几页
     */
    private Integer pageNum = 1;

    /**
     * 页面大小
     */
    private Integer pageSize = Constant.PAGE_SIZE;

    /**
     * 接收多个ID,一般用于批量删除
     */
    private Integer[] ids;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date startTime;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date endTime;
}
