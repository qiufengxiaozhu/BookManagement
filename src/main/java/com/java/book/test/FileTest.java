package com.java.book.test;

import cn.hutool.core.date.*;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * <p>
 * 描述: 文件路径
 * </p>
 *
 * @author 邱高强
 * @since 2020-12-28 14:38
 */
public class FileTest {

    public static void main(String[] args) {

//        System.out.println(System.getProperty("user.dir"));
//
//        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
//        //结果：2017-01-05 12:34:23
//        String dateStr = dateTime.toString();
//        System.out.println(dateStr);
//
//        System.out.println(DateUtil.today());
//        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
//        long id = snowflake.nextId();
//        System.out.println(id);

        // 计算超期天数
        long betweenDay = DateUtil.between(DateUtil.parse("2021-01-19 16:34:23"), new Date(), DateUnit.DAY);
        System.out.println(betweenDay);

        //Level.MINUTE表示精确到分
        String formatBetween = DateUtil.formatBetween(DateUtil.parse("2021-01-19 16:34:23"), new Date(),  BetweenFormater.Level.MINUTE);
        //输出：31天1小时
        Console.log(formatBetween);
    }

}
