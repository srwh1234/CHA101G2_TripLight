package com.tw.game.repository;


import com.tw.game.model.PrizePool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizePoolRepository extends JpaRepository<PrizePool,Integer> {
}
