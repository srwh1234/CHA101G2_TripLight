package com.tw.ai.common;

import java.util.Base64;

public interface GetMethod {

    default  String convertBlobToUrl(byte[] blobData) {
        // 將 BLOB 資料轉換為 Base64 字串編碼
        String base64Data = Base64.getEncoder().encodeToString(blobData);
        // 組合 URL 字串，添加特定的 URL 前綴
        return "data:image/jpeg" + ";base64," + base64Data;
    }
}
