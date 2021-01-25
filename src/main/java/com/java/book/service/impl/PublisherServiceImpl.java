package com.java.book.service.impl;

import com.java.book.entity.Publisher;
import com.java.book.mapper.PublisherMapper;
import com.java.book.service.PublisherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 出版社信息表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Service
public class PublisherServiceImpl extends ServiceImpl<PublisherMapper, Publisher> implements PublisherService {

}
