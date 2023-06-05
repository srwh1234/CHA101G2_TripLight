package com.tw.ticket;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {

	/**
	 * Date 是否在目前時間以前
	 */
	public static boolean isBeforeNow(final Date date) {
		if (date == null) {
			return false;
		}
		return date.before(new Date(System.currentTimeMillis()));
	}

	/**
	 * Date 是否在目前時間以後
	 */
	public static boolean isAfterNow(final Date date) {
		if (date == null) {
			return false;
		}
		return date.after(new Date(System.currentTimeMillis()));
	}

	/**
	 * 目前時間是否在指定Date之間
	 */
	public static boolean isContainNow(final Date begin, final Date end) {
		return isBeforeNow(begin) && isAfterNow(end);
	}

	/**
	 * 回傳目前時間的字串
	 */
	public static String getNowDateTimeString() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}