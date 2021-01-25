package com.java.book.service.impl;

import com.java.book.entity.Menu;
import com.java.book.mapper.MenuMapper;
import com.java.book.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 在权限表中查询所有可用菜单信息
     * @param typeMenu 查询菜单信息
     * @param menuTrue 菜单为可用状态
     * @return list
     */
    @Override
    public List<Menu> getMenuList(String typeMenu, Integer menuTrue) {
        return menuMapper.getMenuList(typeMenu, menuTrue);
    }
}
