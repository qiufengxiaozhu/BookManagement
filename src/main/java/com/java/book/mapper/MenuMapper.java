package com.java.book.mapper;

import com.java.book.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Mapper
@Component
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 在权限表中查询所有可用菜单信息
     * @param typeMenu 查询菜单信息
     * @param menuTrue 菜单为可用状态
     * @return list
     */
    List<Menu> getMenuList(@Param("typeMenu") String typeMenu, @Param("menuTrue") Integer menuTrue);
}
