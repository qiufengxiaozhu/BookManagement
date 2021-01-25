package com.java.book.entity;

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
 * 登录日志表
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_loginfo")
@ApiModel(value="Loginfo对象", description="登录日志表")
public class Loginfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学号+姓名")
    private String loginname;

    @ApiModelProperty(value = "登录ip")
    private String loginip;

    @ApiModelProperty(value = "登录时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date logintime;


}
