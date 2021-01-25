package com.java.book.handler;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description     自动填充处理器
 * @date 2020/7/30 11:36
 */
@Component
public class MyAutoFillHandler implements MetaObjectHandler {

    /**
     * @description insertFill      插入时
     * @date 2020/7/30 11:36
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        // 全局唯一ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        //里面的字段是 实体类中属性名称， 不是数据库字段
        // borrow表
//        this.setFieldValByName("fkTicketid", snowflake.nextId(), metaObject);

        // ticket表
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("returnFlag", 0, metaObject);
        this.setFieldValByName("payFlag", 0, metaObject);
    }

    /**
     * @description updateFill      修改时
     * @date 2020/7/30 11:37
     */
    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
