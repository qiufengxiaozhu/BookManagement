package com.java.book.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回状态
 * @author 邱高强
 */
@Data
public class ResultObj {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    @ApiModelProperty(value = "layui表格封装数据--规定数据总数的字段名称")
    private long total;

    @ApiModelProperty(value = "layui表格/dtree树--规定数据列表的字段名称")
    private long pages;

    @ApiModelProperty(value = "layui表格/dtree树--规定数据列表的字段名称")
    private Object list;


    /**
     * 把构造方法私有化
     */
    private ResultObj() {}

    /**
     * 成功静态方法
     */
    public static ResultObj ok() {
        ResultObj r = new ResultObj();
        r.setSuccess(true);
        r.setCode(Constant.OK);
        r.setMsg(Constant.SUCCESS);
        return r;
    }

    /**
     * 失败静态方法
     */
    public static ResultObj error() {
        ResultObj r = new ResultObj();
        r.setSuccess(false);
        r.setCode(Constant.ERROR);
        r.setMsg(Constant.FAILURE);
        return r;
    }

    public ResultObj success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultObj msg(String message){
        this.setMsg(message);
        return this;
    }

    public ResultObj code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultObj data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultObj data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    /**
     * layui 所需字段
     */
    public ResultObj total(long total) {
        this.setTotal(total);
        return this;
    }
    public ResultObj pages(long pages) {
        this.setPages(pages);
        return this;
    }
    public ResultObj list(Object list) {
        this.setList(list);
        return this;
    }

}
