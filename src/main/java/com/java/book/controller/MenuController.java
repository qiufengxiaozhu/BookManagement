package com.java.book.controller;


import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.common.TreeNode;
import com.java.book.common.TreeNodeBuilder;
import com.java.book.entity.Menu;
import com.java.book.entity.User;
import com.java.book.service.MenuService;
import com.java.book.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api( tags = "菜单管理" )
@RestController
@RequestMapping("menu")
public class MenuController {


    @Autowired
    private MenuService menuService;

    @ApiOperation( value = "首页左侧的菜单树" )
    @GetMapping( "mainMenu" )
    public ResultObj mainMenu() {

        // 1、在权限表中查询所有可用菜单信息
        List<Menu> allMenuList = menuService.getMenuList(Constant.TYPE_MENU, Constant.MENU_TRUE);

        // 从session中获取用户信息
        User user = (User) WebUtils.getSession().getAttribute("user");
        // 2、根据用户的不同权限，获取不同的操作菜单
        List<Menu> menuList = new ArrayList<>();
        if ( user.getType().equals(Constant.USER_TYPE_SUPER) ) {
            // 管理员权限拥有所有菜单
            menuList = allMenuList;
        } else {
            // 根据用户ID+角色+权限去查询
            // 简略处理，flag = 0 的菜单为管理员拥有， flag = 1 的菜单为全部用户拥有
            for ( Menu menu : allMenuList ) {
                if ( Constant.MENU_PREMISSION_NOMAL.equals(menu.getFlag()) ) {
                    menuList.add(menu);
                }
            }
        }

        // 3、查询出所有菜单后，封装到节点树中
        ArrayList<TreeNode> nodeList = new ArrayList<>();
        for ( Menu node : menuList ) {

            // 从 menuList 获取每一个 treeNode 对象
            Integer id = node.getId();
            Integer pid = node.getPid();
            String icon = node.getIcon();
            String title = node.getTitle();
            String href = node.getHref();
            boolean spread = node.getOpen().equals(Constant.MENU_OPEN_TRUE);
            nodeList.add(new TreeNode(id, pid, title, icon, href, spread));
        }

        // 构造层级关系，传入一級菜单节点id
        List<TreeNode> treeNodeList = TreeNodeBuilder.build(nodeList, Constant.MENU_ROOT_NODE);

        return ResultObj.ok().data("menuList", treeNodeList);
    }

}

