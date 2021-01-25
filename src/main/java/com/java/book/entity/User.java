package com.java.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "读者姓名")
    private String name;

    @ApiModelProperty(value = "登录名,学号")
    private String loginname;

    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "专业")
    @TableField("fk_majorId")
    private String fkMajorid;

    @ApiModelProperty(value = "年级")
    @TableField("fk_gradeId")
    private String fkGradeid;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "用户头像地址")
    private String src;

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "用户类型[0超级管理员，1普通用户]")
    private Integer type;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "是否可用，0可用，1已注销")
    private Integer flag;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;


}
