package com.java.book.mapper;

import com.java.book.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 书籍表 Mapper 接口
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Mapper
@Component
public interface BookMapper extends BaseMapper<Book> {

    /**
     * 查询出图书分类比例
     * @return list
     */
    List<Book> getCategoryRatio();
}
