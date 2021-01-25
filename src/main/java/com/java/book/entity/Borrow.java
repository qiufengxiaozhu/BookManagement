package com.java.book.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 借书还书表
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_borrow")
@ApiModel(value="Borrow对象", description="借书还书表")
public class Borrow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学号")
    private String loginname;

    @ApiModelProperty(value = "书籍序列号")
    private Integer booknum;

    @ApiModelProperty(value = "借书时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date borrowTime;

    @ApiModelProperty(value = "预期归还时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date expectTime;

    @ApiModelProperty(value = "实际还书时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    @TableField(strategy = FieldStrategy.IGNORED)   // 为了防止mybatis-plus在设置某个字段为空时自动将其忽略
    private Date returnTime;

    @ApiModelProperty(value = "是否逾期，0->否，1->是")
    private Integer flag;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;

    @ApiModelProperty(value = "罚单表主键id")
//    @TableField(fill = FieldFill.INSERT)
    private Integer fkTicketid;

    @ApiModelProperty(value = "loginname数量")
    @TableField(exist = false)  // 非数据库字段
    private Integer number;

    @ApiModelProperty(value = "虚拟排序列")
    @TableField(exist = false)  // 非数据库字段
    private Integer rank;

}
