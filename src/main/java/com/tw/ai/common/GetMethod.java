package com.tw.ai.common;

import java.util.Base64;

public interface GetMethod {

    default  String convertBlobToUrl(byte[] blobData) {
        // 將 BLOB 資料轉換為 Base64 字串編碼
        String base64Data = Base64.getEncoder().encodeToString(blobData);
        // 組合 URL 字串，添加特定的 URL 前綴
        return "data:image/jpeg" + ";base64," + base64Data;
    }

    default  String extractValue(String input, String prefix) {
        int startIndex = input.indexOf(prefix);   // 開頭
        int endIndex = input.indexOf("\n", startIndex);   // 結尾 (從開頭開始找)

        // 如果找不到結尾
        if (endIndex == -1) {
            // 則設定為最後一個字
            endIndex = input.length();
        }
        // 有開頭，有結尾，則執行擷取動作
        return (startIndex != -1 && endIndex != -1) ? input.substring(startIndex + prefix.length(), endIndex).trim() : null;
    }
}
