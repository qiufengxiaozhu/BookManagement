package com.java.book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value="Menu对象", description="菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父级菜单")
    private Integer pid;

    @ApiModelProperty(value = "权限类型[menu]")
    private String type;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "路径")
    private String href;

    @ApiModelProperty(value = "是否展开，0-否，1-是")
    private Integer open;

    @ApiModelProperty(value = "排序码")
    private Integer ordernum;

    @ApiModelProperty(value = "状态【0可用1不可用】")
    private Integer available;

    @ApiModelProperty(value = "权限【0-超级管理员，1-普通用户】")
    private Integer flag;


}
