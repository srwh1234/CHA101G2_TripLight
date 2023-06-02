package com.tw.ai.repository;


import com.tw.ai.model.AiFavorite;
import com.tw.ai.model.AiLocations;

import java.util.List;

public interface AiFavoriteRepository {
    void save(AiFavorite aiFavorite);
    void save(AiLocations aiLocations);
    List<AiFavorite> findAIFavoriteByMemberId(int memberId);
    int getLastId();

}
