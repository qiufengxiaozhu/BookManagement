package com.java.book.service.impl;

import com.java.book.entity.User;
import com.java.book.mapper.UserMapper;
import com.java.book.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
