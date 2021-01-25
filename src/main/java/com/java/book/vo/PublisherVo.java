package com.java.book.vo;

import com.java.book.common.Constant;
import com.java.book.entity.Category;
import com.java.book.entity.Publisher;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封装 出版社分类 查询条件
 * @author 邱高强
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PublisherVo extends Publisher {

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
}
