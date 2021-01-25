package com.java.book.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 *
 * @author LJH
 *
 */
public class PinyinUtils {

	/**
	 * 返回一个拼音字符串，并且首字母大写
	 */
	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder output = new StringBuilder();
		if (inputString != null && inputString.length() > 0 && !"null".equals(inputString)) {
			char[] input = inputString.trim().toCharArray();
			try {
				for ( char c : input ) {
					if ( Character.toString(c).matches("[\\u4E00-\\u9FA5]+") ) {
						String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
						output.append(temp[0]);
					} else {
						output.append(Character.toString(c));
					}
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			return "*";
		}
		return output.toString();
	}

	public static void main(String[] args) {
		String yin = getPingYin("秋枫");
		System.out.println(yin);
	}
}
