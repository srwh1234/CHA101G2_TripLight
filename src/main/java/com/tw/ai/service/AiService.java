package com.tw.ai.service;


import com.tw.ai.common.ChatGPTAPI;
import com.tw.ai.common.GetLocation;
import com.tw.ai.common.GetMethod;
import com.tw.ai.common.dto.AiFormData;
import com.tw.ai.common.dto.Location;
import com.tw.ai.dao.AiFavoriteRepository;
import com.tw.ai.entity.AiFavorite;
import com.tw.ai.entity.AiLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AiService implements GetMethod {

    private final AiFavoriteRepository aiFavoriteRepository;
    private final ChatGPTAPI chatGPTAPI;
    private final GetLocation getLocation;
    private final Map<String, AiFormData> formDataList;
    private int id;
    private final Map<String, Long> lastHeartbeatMap;

    @Autowired
    public AiService(AiFavoriteRepository aiFavoriteRepository, ChatGPTAPI chatGPTAPI, Map<String, AiFormData> formDataList, GetLocation getLocation) {
        this.aiFavoriteRepository = aiFavoriteRepository;
        this.chatGPTAPI = chatGPTAPI;
        this.getLocation = getLocation;
        this.formDataList = formDataList;
        this.lastHeartbeatMap = new ConcurrentHashMap<>();
        id = getLastId();
    }

    public int save(String resultData, String resultUrl, String memberId) {

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

        aiFavoriteRepository.save(aiFavorite);
        System.out.println("存入資料的ID:" + aiFavorite.getAiFavoriteId());

        return aiFavorite.getAiFavoriteId();
    }

    public void saveLocation(String memberId, int aiFavoriteId) {
        var locationList = getLocation.locations.get(memberId);

        System.out.println(locationList);
        for (var location : locationList) {
            var locations = new AiLocations();
            locations.setAiFavoriteId(aiFavoriteId);
            locations.setLocationTitle(location.getLocationTitle());
            locations.setLatitude(location.getLatitude());
            locations.setLongitude(location.getLongitude());
            aiFavoriteRepository.save(locations);
            System.out.println("存入地點資料:" + locations);
        }
    }

    public int getLastId() {
        return aiFavoriteRepository.getLastId();
    }

    public List<AiFavorite> findAIFavoriteFromMemberId(int memberId) {
        return aiFavoriteRepository.findAIFavoriteFromMemberId(memberId);
    }

    public void startChatGPT(String memberId, AiFormData formData) {
        chatGPTAPI.start(memberId, formData);
    }

    public String getChatGPTResult(String memberId) {
        return chatGPTAPI.getOutput(memberId);
    }

    public ArrayList<Location> getLatitudeAndLongitude(String memberId) {
        var locations = chatGPTAPI.locations.get(memberId);
        // 將地點轉成經緯度 如果為空陣列，就不要執行了
        if (locations != null && !locations.isEmpty()) {
            getLocation.start(memberId, locations);
            return getLocation.locations.get(memberId);
        }
        return null;
    }

    public ArrayList<String> getChatGPTLocations(String memberId) {
        return chatGPTAPI.locations.get(memberId);
    }

    public void clearContent(String memberId) {
        chatGPTAPI.getOutput().remove(memberId);
        chatGPTAPI.locations.remove(memberId);
        formDataList.remove(memberId);
        getLocation.locations.remove(memberId);
        lastHeartbeatMap.remove(memberId);
    }

    public void setFormDataList(String memberId, AiFormData formData) {
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
        System.out.println("顯示成員名單：" + lastHeartbeatMap.toString());
        long currentTime = System.currentTimeMillis();  // 獲得1970年起至今的毫秒數
        long heartbeatThreshold = 10000; // 心跳閾值，單位為毫秒

        // 遍歷鍵值對
        for (Map.Entry<String, Long> entry : lastHeartbeatMap.entrySet()) {
            String memberId = entry.getKey();
            long lastHeartbeatTime = entry.getValue();
            if (currentTime - lastHeartbeatTime >= heartbeatThreshold) {
                clearContent(memberId);
                System.out.println("執行清空操作，清空成員ID： " + memberId);
            }
        }
    }

}
