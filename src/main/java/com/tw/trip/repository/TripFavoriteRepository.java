package com.tw.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripFavorite;
import com.tw.trip.pojo.TripFavorite.PrimaryKey2;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TripFavoriteRepository extends JpaRepository<TripFavorite, PrimaryKey2> {
	public List<TripFavorite> findByKeyMemberId(int memberId);

	// 刪除
	public void deleteByKeyMemberId(int memberId);

	@Modifying
	@Query("DELETE FROM TripFavorite tf WHERE tf.key.memberId = :memberId AND tf.key.trip.tripId = :tripId")
	void deleteQuery(@Param("memberId") int memberId, @Param("tripId") int tripId);

	// public void deleteByKeyTrip(PrimaryKey2 key);

	// public TripFavorite findByKeyMemberId(int memberId);
	public TripFavorite findByKeyTrip(Trip trip);

	// public TripFavorite findByKeyTripId(int tripId);

}
