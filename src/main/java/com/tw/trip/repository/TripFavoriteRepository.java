package com.tw.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripFavorite;
import com.tw.trip.pojo.TripFavorite.PrimaryKey2;	

@Repository
public interface TripFavoriteRepository extends JpaRepository<TripFavorite,PrimaryKey2>{
	public List<TripFavorite> findByKeyMemberId(int memberId);

	// 刪除
	public void deleteByKeyMemberId(int memberId);

//	public void deleteByKeyTrip(PrimaryKey2 key);
	public void deleteByKeyTrip(PrimaryKey2 key);

	//public TripFavorite findByKeyMemberId(int memberId);
	public TripFavorite findByKeyTrip(Trip trip);
	
//	public TripFavorite findByKeyTripId(int tripId);

}
