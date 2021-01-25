package com.java.book.service.impl;

import com.java.book.entity.Loginfo;
import com.java.book.mapper.LoginfoMapper;
import com.java.book.service.LoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

}
