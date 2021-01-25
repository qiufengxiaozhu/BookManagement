package com.java.book.mapper;

import com.java.book.entity.Borrow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 借书还书表 Mapper 接口
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
@Mapper
@Component
public interface BorrowMapper extends BaseMapper<Borrow> {

    /**
     * 查询出借阅前十排行
     * @return list
     */
    List<Borrow> getTopTenRank();
}
