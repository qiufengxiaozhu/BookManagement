package com.java.book.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * layui接收json数据实体
 *
 * @author 邱高强
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView {

	// 返回成功状态码
	private Integer code=0;
	// 返回信息
	private String msg="";
	// 数据条数
	private Long count=0L;
	// 封装数据
	private Object data;

	public DataGridView(Long count, Object data) {
		super();
		this.count = count;
		this.data = data;
	}
	public DataGridView(Object data) {
		super();
		this.data = data;
	}
}
