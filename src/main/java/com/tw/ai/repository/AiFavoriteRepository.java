package com.tw.ai.repository;

import com.tw.ai.model.AiFavorite;
import java.util.List;
public interface AiFavoriteRepository {
    void save(AiFavorite aiFavorite);
    List<AiFavorite> findAIFavoriteByMemberId(int memberId);
    int getLastId();
    Boolean delete(Integer aiFavoriteId);
}
