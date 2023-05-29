package com.tw.ai.dao;


import com.tw.ai.entity.aIFavorite.AiFavorite;
import com.tw.ai.entity.aIFavorite.AiLocations;

import java.util.List;

public interface DataDAO {
    void save(AiFavorite aiFavorite);
    void save(AiLocations aiLocations);
    List<AiFavorite> findAIFavoriteFromMemberId(int memberId);
    int getLastId();

}
