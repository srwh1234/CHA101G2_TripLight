package com.tw.trip.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.trip.controller.TripFavoriteController.DetailDto;
import com.tw.trip.controller.TripFavoriteController.FavoriteReqDto;
import com.tw.trip.pojo.Trip;
import com.tw.trip.pojo.TripFavorite;
import com.tw.trip.pojo.TripFavorite.PrimaryKey2;
import com.tw.trip.repository.TripFavoriteRepository;
import com.tw.trip.repository.TripImageRepository;
import com.tw.trip.repository.TripRepository;

@Service
public class TripFavoriteService {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private TripFavoriteRepository tripFavoriteRepository;

	@Autowired
	private TripImageRepository tripImageRepository;

	@Autowired
	private TripService tripService;

	public static int FAVORITE_ADD_OK = 1; // 成功加入我的最愛
	public static int FAVORITE_DEL_OK = 2; // 已從我的最愛移除
	public static int FAVORITE_LOGIN = 3; // 需要登入
	public static int FAVORITE_ERROR = 4; // 發生未知的錯誤

	;

	/**
	 * 更新我的最愛的狀態
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public int updateItem(final FavoriteReqDto reqDto) {
		final Member member = memberRepository.findById(reqDto.getMemberId()).orElse(null);
		if (member == null) {
			return FAVORITE_LOGIN;
		}

		Trip trip = tripRepository.findById(reqDto.getTripId()).orElse(null);

		if (trip == null) {
			return FAVORITE_ERROR;
		}
		// 如果是已經收藏的 就取消收藏
		 boolean isFavorite = tripFavoriteRepository.existsById(new PrimaryKey2(reqDto.getMemberId(), trip));
		if (isFavorite) {
//			tripFavoriteRepository.deleteById(new PrimaryKey2(reqDto.getMemberId(), trip));
			tripFavoriteRepository.deleteById(new PrimaryKey2(reqDto.getMemberId(),trip));
			System.out.println("delete");
			return FAVORITE_DEL_OK;
		}
		
		final TripFavorite favorite = new TripFavorite();
		favorite.setKey(new PrimaryKey2(reqDto.getMemberId(), trip));
		favorite.setAddTime(Timestamp.from(Instant.now()));
		System.out.println("加入收藏");
		tripFavoriteRepository.save(favorite);

		return FAVORITE_ADD_OK;

	}


	// 判斷是不是已經收藏的

//	public boolean checkIfExists(Integer memberId, Trip trip) {
//		 List<TripFavorite> findMember = tripFavoriteRepository.findByKeyMemberId(memberId);
//	        for (TripFavorite favorite : findMember) {
//	            if (favorite.getKey().getTrip().equals(trip)) {
//	                return true; // 資料存在
//	            }
//	        }
//	        return false; // 資料不存在
//	    
//	}
//	
	public boolean checkIfExists(FavoriteReqDto reqDto) {
		Trip trip = tripRepository.findById(reqDto.getTripId()).orElse(null);
		boolean isFavorite = tripFavoriteRepository.existsById(new PrimaryKey2(reqDto.getMemberId(),trip));
		System.out.println(isFavorite);
		return isFavorite;
	}
	
	
	
	

	public DetailDto getAllTripItem(final int memberId, final int tripId) {
		final Trip trip = tripRepository.findById(tripId).orElse(null);

		if (trip == null) {
			return null;
		}
		final DetailDto detailDto = new DetailDto(trip);
		// 我的最愛
		detailDto.setFavorite(tripFavoriteRepository.existsById(new PrimaryKey2(memberId, trip)));
		return detailDto;
	}
}
