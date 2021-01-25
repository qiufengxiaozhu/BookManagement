package com.java.book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 书籍表
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_book")
@ApiModel(value = "Book对象", description = "书籍表")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "书籍序列号")
	private Integer booknum;

	@ApiModelProperty(value = "书名")
	private String name;

	@ApiModelProperty(value = "出版社")
	@TableField("fk_publisherId")
	private String fkPublisherid;

	@ApiModelProperty(value = "出版时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date publishTime;

	@ApiModelProperty(value = "封面路径")
	private String src;

	@ApiModelProperty(value = "封面标题")
	private String alt;

	@ApiModelProperty(value = "纯文本内容")
	private String text;

	@ApiModelProperty(value = "富文本内容")
	private String content;

	@ApiModelProperty(value = "作者")
	private String author;

	@ApiModelProperty(value = "分类")
	@TableField("fk_categoryId")
	private String fkCategoryid;

	@ApiModelProperty(value = "库存")
	private Integer number;

	@ApiModelProperty(value = "上架时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTime;

	@ApiModelProperty(value = "修改时间")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date updateTime;

	@ApiModelProperty(value = "是否下架,0->未上架，1->已上架")
	private Integer putaway;

	@ApiModelProperty(value = "是否删除,0->未删除，1->已删除")
	private Integer flag;

	@ApiModelProperty(value = "分类名")
	@TableField(exist = false)	// 非数据库字段
	private String categoryName;

	@ApiModelProperty(value = "category数量")
	@TableField(exist = false)  // 非数据库字段
	private Integer categoryNumber;

}
