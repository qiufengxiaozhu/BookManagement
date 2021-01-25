package com.java.book.service.impl;

import com.java.book.entity.Book;
import com.java.book.mapper.BookMapper;
import com.java.book.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 书籍表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 查询出图书分类比例
     *
     * @return list of
     */
    @Override
    public List<Book> getCategoryRatio() {
        return bookMapper.getCategoryRatio();
    }
}
