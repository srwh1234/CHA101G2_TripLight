package com.tw.ticket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyUtils {
	/**
	 * 判斷字串是否為空值或空白或全空格
	 */
	public static boolean isEmpty(final String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

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

	/**
	 * 關閉實現 AutoCloseable 接口的物件
	 */
	public static void close(final AutoCloseable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (final Exception e) {
			// ignore
		}
	}

	/**
	 * 關閉實現 AutoCloseable 接口的物件們
	 */
	public static void close(final AutoCloseable... closeables) {
		if (closeables != null) {
			for (final AutoCloseable c : closeables) {
				close(c);
			}
		}
	}

	/**
	 * json物件的轉換
	 */
	public static final Gson GSON = new GsonBuilder().create();

	public static <T> T fromJson(final String json, final Class<T> classOfT) {
		return GSON.fromJson(json, classOfT);
	}

	public static String toJson(final Object src) {
		return GSON.toJson(src);
	}
}
