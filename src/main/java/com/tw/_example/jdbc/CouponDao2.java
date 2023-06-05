package com.tw._example.jdbc;

import com.tw._example.hibernate.BaseDao;
import com.tw.ticket.model.Coupon;

public interface CouponDao2 extends BaseDao<Coupon> {
	// 已經繼承了BaseDao抽象方法了
	// 如果有其他額外方法請寫在下面
}
