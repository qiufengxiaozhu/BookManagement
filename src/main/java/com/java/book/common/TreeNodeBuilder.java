package com.java.book.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱高强
 */
public class TreeNodeBuilder {


    /**
     * 把没有层级关系的集合变成有层级关系的
     *
     * @param nodeList 菜单集合
     * @param rootId 一级菜单节点
     * @return List<TreeNode>
     */
    public static List<TreeNode> build(List<TreeNode> nodeList, Integer rootId) {
        List<TreeNode> nodes = new ArrayList<>();
        for ( TreeNode n1 : nodeList ) {
            if ( n1.getPid().equals(rootId) ) {
                nodes.add(n1);
            }
            for ( TreeNode n2 : nodeList ) {
                if ( n1.getId().equals(n2.getPid()) ) {
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}
