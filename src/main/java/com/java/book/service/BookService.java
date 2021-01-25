package com.java.book.service;

import com.java.book.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 书籍表 服务类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
public interface BookService extends IService<Book> {

    /**
     * 查询出图书分类比例
     * @return list of
     */
    List<Book> getCategoryRatio();
}
