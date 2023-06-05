package com.tw.ai.service;


import com.tw.ai.util.GetLocation;
import com.tw.ai.dto.AiFormDataDto;
import com.tw.ai.dto.AiLocationsDto;
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
    public AiService(AiFavoriteRepository aiFavoriteRepository, ChatGPTService chatGPTService, Map<String, AiFormDataDto> formDataList, GetLocation getLocation) {
        this.aiFavoriteRepository = aiFavoriteRepository;
        this.chatGPTService = chatGPTService;
        this.getLocation = getLocation;
        this.formDataList = formDataList;
        this.lastHeartbeatMap = new ConcurrentHashMap<>();
        id = getLastId();
    }

    public int save(String resultData, String resultUrl, String memberId) {
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
        aiFavorite.setMemberId(1);  // TODO:
        aiFavorite.setPlanningDescription(resultData);
        aiFavorite.setRoute(resultUrl);

        var locationList = getLocation.locations.get(memberId);
        var aiLocations = new ArrayList<AiLocations>();
        for (var location : locationList) {
            var locations = new AiLocations();
            locations.setAiFavoriteId(aiFormData.getFormId());
            locations.setLocationTitle(location.getLocationTitle());
            locations.setLatitude(location.getLatitude());
            locations.setLongitude(location.getLongitude());
            aiLocations.add(locations);
        }
        aiFavorite.setAiLocations(aiLocations);


        aiFavoriteRepository.save(aiFavorite);
        logger.info("存入資料的ID:" + aiFavorite.getAiFavoriteId());

        return aiFavorite.getAiFavoriteId();}
        catch (Exception e){
            logger.error("表單資料為空");
            return -1;
        }
    }

    public int getLastId() {
        return aiFavoriteRepository.getLastId();
    }

    public List<AiFavorite> findAIFavoriteFromMemberId(int memberId) {
        return aiFavoriteRepository.findAIFavoriteByMemberId(memberId);
    }

    public void startChatGPT(String memberId, AiFormDataDto formData) {
        logger.info("執行chatGPT");
        chatGPTService.start(memberId, formData);
    }

    public String getChatGPTResult(String memberId) {
        return chatGPTService.getOutput(memberId);
    }

    public ArrayList<AiLocationsDto> getLatitudeAndLongitude(String memberId) {
        var locations = chatGPTService.locations.get(memberId);
        // 將地點轉成經緯度 如果為空陣列，就不要執行了
        if (locations != null && !locations.isEmpty()) {
            getLocation.start(memberId, locations);
            return getLocation.locations.get(memberId);
        }
        return null;
    }

    public ArrayList<String> getChatGPTLocations(String memberId) {
        return chatGPTService.locations.get(memberId);
    }

    public void clearContent(String memberId) {
        chatGPTService.getOutput().remove(memberId);
        chatGPTService.locations.remove(memberId);
        formDataList.remove(memberId);
        getLocation.locations.remove(memberId);
        lastHeartbeatMap.remove(memberId);
        logger.info(memberId+"執行清空作業");
    }

    public void setFormDataList(String memberId, AiFormDataDto formData) {
        logger.info("接收表單資料");
        formDataList.put(memberId, formData);
    }


    public int getFormId(){
        id++;
        return id;
    }

    public String getDestination(String memberId) {
        return formDataList.get(memberId).getDestination();
    }

    public void updateHeartbeat(String memberId) {
        lastHeartbeatMap.put(memberId, System.currentTimeMillis());
    }

    public void checkHeartbeat() {
        logger.info("顯示成員名單：" + lastHeartbeatMap.toString());
        long currentTime = System.currentTimeMillis();  // 獲得1970年起至今的毫秒數
        long heartbeatThreshold = 10000; // 心跳閾值，單位為毫秒

        // 遍歷鍵值對
        for (Map.Entry<String, Long> entry : lastHeartbeatMap.entrySet()) {
            String memberId = entry.getKey();
            long lastHeartbeatTime = entry.getValue();
            if (currentTime - lastHeartbeatTime >= heartbeatThreshold) {
                clearContent(memberId);
                logger.info(memberId+"執行清空作業");
            }
        }
    }
}
