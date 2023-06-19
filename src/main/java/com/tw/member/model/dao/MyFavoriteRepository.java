package com.tw.member.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.member.model.Favorite;

public interface MyFavoriteRepository extends JpaRepository<Favorite, Integer> {

}
