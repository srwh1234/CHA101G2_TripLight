package com.tw._example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tw.ticket.MyUtils;
import com.tw.ticket.model.Coupon;

@Repository
public class CouponDao2Impl implements CouponDao2 {

	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(final Coupon t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(final Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(final Coupon t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Coupon selectById(final Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coupon> selectAll() {
		final List<Coupon> result = new ArrayList<>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			final String sql = "SELECT * FROM `coupon` ORDER BY `coupon_id`";

			con = dataSource.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				final Coupon coupon = new Coupon();
				coupon.setCouponId(rs.getInt("coupon_id"));
				coupon.setName(rs.getString("name"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setExpiryDate(rs.getDate("expiry_date"));
				coupon.setDiscount(rs.getInt("discount"));
				result.add(coupon);
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			MyUtils.close(rs, ps, con);
		}
		return result;
	}

}
