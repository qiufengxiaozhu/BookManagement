package com.java.book.service.impl;

import com.java.book.entity.Borrow;
import com.java.book.mapper.BorrowMapper;
import com.java.book.service.BorrowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 借书还书表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    /**
     * 查询出借阅前十排行
     *
     * @return list
     */
    @Override
    public List<Borrow> getTopTenRank() {
        return borrowMapper.getTopTenRank();
    }
}
