package com.tw.ai.dao;


import com.tw.ai.entity.AiFavorite;
import com.tw.ai.entity.AiLocations;

import java.util.List;

public interface AiFavoriteRepository {
    void save(AiFavorite aiFavorite);
    void save(AiLocations aiLocations);
    List<AiFavorite> findAIFavoriteFromMemberId(int memberId);
    int getLastId();

}
