package com.tw.ai.repository;

import com.tw.ai.model.AiFavorite;
import java.util.List;
public interface AiFavoriteRepository {
    void save(AiFavorite aiFavorite);   // 儲存
    List<AiFavorite> findAIFavoriteByMemberId(int memberId);  // 搜尋
    int getLastId();  // 獲得最後一個編號
    void deleteAiFavorite(Integer aiFavoriteId);  // 刪除

    void deleteAiLocations(Integer aiFavoriteId); //

}
