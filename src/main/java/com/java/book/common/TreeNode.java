package com.java.book.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装后台左侧菜单树、部门树
 *
 * @author 邱高强
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

    private Integer id;
//    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;

    /**
     * 二级子菜单
     */
    private List<TreeNode> children = new ArrayList<TreeNode>();

    /**
     * 首页左边导航菜单的构造器
     */
    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    /**
     * 部门树的构造器
     *
     * {
     * "code":0,
     * "msg":"操作成功",
     * "data": [
     *   {"id":"001","title": "湖南省","checkArr": "0","parentId": "0"},
     *   {"id":"002","title": "湖北省","checkArr": "0","parentId": "0"},
     *   {"id":"003","title": "广东省","checkArr": "0","parentId": "0"},
     *   {"id":"004","title": "浙江省","checkArr": "0","parentId": "0"},
     *   {"id":"005","title": "福建省","checkArr": "0","parentId": "0"},
     *   {"id":"001001","title": "长沙市","checkArr": "0","parentId": "001"},
     *   {"id":"001002","title": "株洲市","checkArr": "0","parentId": "001"},
     *   {"id":"001003","title": "湘潭市","checkArr": "0","parentId": "001"},
     *   {"id":"001004","title": "衡阳市","checkArr": "0","parentId": "001"},
     *   {"id":"001005","title": "郴州市","checkArr": "0","iconClass": "dtree-icon-caidan_xunzhang","parentId": "001"}
     * ]
     * }
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }


}
