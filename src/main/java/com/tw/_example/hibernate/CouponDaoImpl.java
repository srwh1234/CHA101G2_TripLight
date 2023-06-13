package com.tw._example.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tw.ticket.model.Coupon;

import jakarta.persistence.PersistenceContext;

@Transactional // 正常要加在Service
@Repository
public class CouponDaoImpl implements CouponDao {

	@PersistenceContext
	private Session session;

	@Override
	public void insert(final Coupon t) {
		session.persist(t);
	}

	@Override
	public void deleteById(final Integer id) {
		final Coupon coupon = session.get(Coupon.class, id);
		if (coupon != null) {
			session.remove(coupon);
		}
	}

	@Override
	public void update(final Coupon t) {
		session.merge(t);
		session.flush();
	}

	@Override
	public Coupon selectById(final Integer id) {
		return session.get(Coupon.class, id); // for query via OID
	}

	@Override
	public List<Coupon> selectAll() {
		final String hql = "FROM Coupon ORDER BY couponId";
		return session.createQuery(hql, Coupon.class).getResultList();
	}

}
