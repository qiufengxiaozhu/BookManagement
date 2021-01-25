package com.java.book.util;

/**
 *
 * 中文生成器
 * @author 邱高强
 */
public class ChineseUtil {

    /**
     * 返回一个汉字
     */
    public static char getRandomHanZi() {
        return (char) ( 0x4e00 + (int) ( Math.random() * ( 0x9fa5 - 0x4e00 + 1 ) ) );
    }

    /**
     * 获取多个汉字中间有空格
     *
     * @param size 汉字个数
     */
    public static String getRandomChinese(int size) {

        if ( size <= 0 ) {
            size = 1;
        }
        StringBuilder stringBuffer = new StringBuilder();

        for ( int i = 0; i < size; i++ ) {
            stringBuffer.append(getRandomHanZi()).append(" ");
        }
        return stringBuffer.toString();
    }

    /**
     * 获取多个汉字中间无空格
     *
     * @param size 汉字个数
     */
    public static String getRandomChineseNoSpace(int size) {

        if ( size <= 0 ) {
            size = 1;
        }
        StringBuilder stringBuffer = new StringBuilder();

        for ( int i = 0; i < size; i++ ) {
            stringBuffer.append(getRandomHanZi());
        }
        return stringBuffer.toString();
    }
}
