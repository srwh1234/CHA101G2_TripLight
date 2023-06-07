package com.tw.ai.service;


import com.tw.ai.util.GetLocation;
import com.tw.ai.dto.AiFormDataDto;
import com.tw.ai.repository.AiFavoriteRepository;
import com.tw.ai.model.AiFavorite;
import com.tw.ai.model.AiLocations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AiService {

    private final AiFavoriteRepository aiFavoriteRepository;
    private final ChatGPTService chatGPTService;
    private final GetLocation getLocation;
    private final Map<String, AiFormDataDto> formDataList;
    private int id;
    private final Map<String, Long> lastHeartbeatMap;

    private final Logger logger
            = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AiService(AiFavoriteRepository aiFavoriteRepository, ChatGPTService chatGPTService, GetLocation getLocation) {
        this.aiFavoriteRepository = aiFavoriteRepository;
        this.chatGPTService = chatGPTService;
        this.getLocation = getLocation;
        this.formDataList = new ConcurrentHashMap<>();
        this.lastHeartbeatMap = new ConcurrentHashMap<>();
        id = getLastId();
    }

    public boolean save(String resultData, String resultUrl, String memberId,Integer userId) {
        try {
            var aiFormData = formDataList.get(memberId);
            AiFavorite aiFavorite = new AiFavorite();

            aiFavorite.setAiFavoriteId(aiFormData.getFormId());
            aiFavorite.setDestination(aiFormData.getDestination());
            aiFavorite.setTravelDays(aiFormData.getTravelDays());
            aiFavorite.setPeople(aiFormData.getPeople());
            aiFavorite.setBudgetRange(aiFormData.getBudgetRange());
            aiFavorite.setPreferredStyle(aiFormData.getPreferredStyle());
            aiFavorite.setOtherDemands(aiFormData.getOtherDemands());
            aiFavorite.setMemberId(userId);  // TODO:
            aiFavorite.setPlanningDescription(resultData);
            aiFavorite.setRoute(resultUrl);

            var locationList = getLocation.locations.get(memberId);
            var aiLocations = new ArrayList<AiLocations>();
            // 如果為空值，就不執行後續動作
            if (locationList != null) {
                for (var location : locationList) {
                    var locations = new AiLocations();
                    locations.setAiFavoriteId(aiFormData.getFormId());
                    locations.setLocationTitle(location.getLocationTitle());
                    locations.setLatitude(location.getLatitude());
                    locations.setLongitude(location.getLongitude());
                    aiLocations.add(locations);
                }
            }

            aiFavorite.setAiLocations(aiLocations);


            aiFavoriteRepository.save(aiFavorite);
            logger.info("存入AiFavorite資料, ID:" + aiFavorite.getAiFavoriteId());

            return true;
        } catch (Exception e) {
            logger.warn("Ai表單資料為空：資料庫儲存失敗");
            return false;
        }
    }

    public boolean delete(int aiFavoriteId){
        return aiFavoriteRepository.delete(aiFavoriteId);
    }

    public int getLastId() {
        return aiFavoriteRepository.getLastId();
    }

    public List<AiFavorite> findAIFavoriteFromMemberId(int memberId) {
        return aiFavoriteRepository.findAIFavoriteByMemberId(memberId);
    }

    public void clearContent(String memberId) {
        chatGPTService.getOutput().remove(memberId);
        chatGPTService.locations.remove(memberId);
        formDataList.remove(memberId);
        getLocation.locations.remove(memberId);
        lastHeartbeatMap.remove(memberId);
        logger.info(memberId + "執行清空作業");
    }

    public void setFormDataList(String memberId, AiFormDataDto formData) {
        logger.info("接收表單資料");
        formDataList.put(memberId, formData);
    }

    public int getFormId() {
        id++;
        return id;
    }

    public String getDestination(String memberId) {
        var aiFormDataDto = formDataList.get(memberId);
        if (aiFormDataDto != null) {
            return aiFormDataDto.getDestination();
        } else {
            logger.warn("表單資料為空, 無法推薦票券與行程");
            return null;
        }
    }

    public void updateHeartbeat(String memberId) {
        lastHeartbeatMap.put(memberId, System.currentTimeMillis());
    }

    public void checkHeartbeat() {
        logger.info("顯示成員名單：" + lastHeartbeatMap.toString());
        long currentTime = System.currentTimeMillis();  // 獲得1970年起至今的毫秒數
        long heartbeatThreshold = 60000; // 心跳閾值，單位為毫秒，每分鐘

        // 遍歷鍵值對
        for (Map.Entry<String, Long> entry : lastHeartbeatMap.entrySet()) {
            String memberId = entry.getKey();
            long lastHeartbeatTime = entry.getValue();
            if (currentTime - lastHeartbeatTime >= heartbeatThreshold) {
                clearContent(memberId);
                logger.info(memberId + "執行清空作業");
            }
        }
    }
}
