package com.java.book.service;

import com.java.book.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
public interface MenuService extends IService<Menu> {

    /**
     * 在权限表中查询所有可用菜单信息
     * @param typeMenu 查询菜单信息
     * @param menuTrue 菜单为可用状态
     * @return list
     */
    List<Menu> getMenuList(String typeMenu, Integer menuTrue);
}
