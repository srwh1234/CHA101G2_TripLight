package com.tw.vendor.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tw.vendor.dao.TripRepository2;

import jakarta.persistence.Table;

@Table(name = "Trip")
@Component // 給Spring IoC 託管
public class TripMain2 implements CommandLineRunner { // 這個class會在伺服器部屬完執行(main方法)

	@Autowired // 注入tripRepository實例 (dao)
	private TripRepository2 tripRepository2;

	@Override
	public void run(final String... args) {
//		final var trips = tripRepository2.findAll();
//		System.out.println(trips); // 顯示查詢到的資料
	}

}
