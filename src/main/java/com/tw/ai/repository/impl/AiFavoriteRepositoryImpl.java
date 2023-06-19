package com.tw.ai.repository.impl;

import com.tw.ai.model.AiFavorite;
import com.tw.ai.model.AiLocations;
import com.tw.ai.repository.AiFavoriteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;

// Spring bean
// 自動注入EntityManager
@Repository
public class AiFavoriteRepositoryImpl implements AiFavoriteRepository {
    private final EntityManager entityManager;  // 宣告entityManager

    @Autowired
    public AiFavoriteRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(AiFavorite aiFavorite) {
        // 如果用merge()  沒有該PK則新增，有則覆蓋
        entityManager.merge(aiFavorite);
    }

    public int getLastId() {
        // 從資料庫獲取最後一個 ID 的值
        Query query = entityManager.createQuery("SELECT MAX(a.aiFavoriteId) FROM AiFavorite a");
        Integer lastId = (Integer) query.getSingleResult();

        // 如果最後一個 ID 為空，則將 ID 設置為 1
        // 獲得最後一個 ID 的值
        return Objects.requireNonNullElse(lastId, 1);
    }


    @Transactional
    public void deleteAiFavorite(Integer aiFavoriteId) {
        AiFavorite aiFavorite = entityManager.find(AiFavorite.class, aiFavoriteId);
        if (aiFavorite != null) {
            entityManager.remove(aiFavorite);
        }
    }
    @Transactional
    public void deleteAiLocations(Integer aiFavoriteId) {
        TypedQuery<AiLocations> theQuery = entityManager.createQuery("FROM AiLocations a WHERE a.aiFavoriteId = :aiFavoriteId", AiLocations.class);
        theQuery.setParameter("aiFavoriteId", aiFavoriteId);
        var resultList = theQuery.getResultList();
        for(var result:resultList){
            entityManager.remove(result);
        }
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM AiFavorite a";
        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
        return typedQuery.getSingleResult();
    }


    public List<AiFavorite> findAIFavoriteByMemberId(int memberId) {

        // 使用entityManager 呼叫 createQuery 放入 JPQL指令
        TypedQuery<AiFavorite> theQuery = entityManager.createQuery("FROM AiFavorite a WHERE a.memberId = :memberId", AiFavorite.class);
        // 放入變數
        theQuery.setParameter("memberId", memberId);
        // 取得 ResultList
        return theQuery.getResultList();

    }




}
