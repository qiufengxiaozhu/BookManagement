package com.java.book.entity;

import java.math.BigDecimal;

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
 * 罚单信息表
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_ticket")
@ApiModel(value="Ticket对象", description="罚单信息表")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id",type = IdType.INPUT) //这种方式是主键手动输入
    private Integer id;

    @ApiModelProperty(value = "学号")
    private String loginname;

    @ApiModelProperty(value = "书籍序列号")
    private Integer booknum;

    @ApiModelProperty(value = "超期天数")
    private Integer overDate;

    @ApiModelProperty(value = "单天逾期金额")
    private BigDecimal overFee;

    @ApiModelProperty(value = "处罚金额")
    private BigDecimal ticketFee;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;

    @ApiModelProperty(value = "是否归还，0->否，1->是")
    @TableField(fill = FieldFill.INSERT)
    private Integer returnFlag;

    @ApiModelProperty(value = "是否支付，0->否，1->是")
    @TableField(fill = FieldFill.INSERT)
    private Integer payFlag;


}
