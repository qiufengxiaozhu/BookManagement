package com.java.book.util;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 描述: 时间日期工具类
 * </p>
 *
 * @author 邱高强
 * @since 2020-12-28 22:51
 */
public class DateUtils {

    public static void main(String[] args) throws ParseException {
//        dateFile();

        boolean result = compare(DateUtil.parse("2021-01-19 16:40:16"), DateUtil.parse(DateUtil.today() + " 23:59:59"));
        System.out.println(result);
    }

    /**
     * 当前时间：2020/12/28/
     *
     * @return 返回文件上传文件夹格式
     */
    public static String dateFile() {

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        return sdf.format(d);
    }

    /**
     * 比较两个日期大小
     * a 小于 b 返回true
     * @param start 开始时间
     * @param end 结束时间
     * @return boolean
     * @throws ParseException 1
     */
    public static boolean compare(Date start, Date end) throws ParseException {

        return start.before(end);
    }
}
