package com.java.book.service;

import com.java.book.entity.Borrow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 借书还书表 服务类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
public interface BorrowService extends IService<Borrow> {

    /**
     * 查询出借阅前十排行
     * @return list
     */
    List<Borrow> getTopTenRank();
}
