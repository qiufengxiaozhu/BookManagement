package com.java.book.service.impl;

import com.java.book.entity.Category;
import com.java.book.mapper.CategoryMapper;
import com.java.book.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 书籍分类表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
