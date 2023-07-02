package com.tw.contact.testChat;

import com.alibaba.fastjson.JSON;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//参数role判断用户角色0是客服，1是用户
@ServerEndpoint(value = "/websocket/{role}")
@Component
public class SocketController {

    //用本地线程保存session
    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();
    //保存上線客戶 String放SessionID
    private static Map<String, SocketUserInfo> userSessionMap = new ConcurrentHashMap<>();
    //保存上線客服 String放SessionID
    private static Map<String, SocketUserInfo> serverSessionMap = new ConcurrentHashMap<>();

    //連線設定
    @OnOpen
    public void onOpen(Session session, @PathParam(value="role") Integer role) {

        //設定狀態map
        Map<String, String> state = new HashMap<>();
        state.put("state", "error");

        //執行緒獨立
        sessions.set(session);

        //客服上線的情況
        if (role.equals(0)) {
            //建立一個客服資訊
            SocketUserInfo serverInfo = new SocketUserInfo();
            serverInfo.setSessionId(session.getId());
            serverInfo.setSession(session);
            serverInfo.setUserRole("客服");

            //客服連線狀態為成功
            state.put("state", "success");

            //查詢是否有客戶正在排隊?
            //有:進入 無:不進入
            if (findLineUser() != null){
                //透過會話id取得客戶資訊
                SocketUserInfo userInfo = userSessionMap.get(findLineUser());
                //將客戶的目標會話id綁定(將客戶綁定客服)
                userInfo.setTargetSessionId(serverInfo.getSessionId());
                //將客服的目標會話id綁定(將客服綁定客戶)
                serverInfo.setTargetSessionId(userInfo.getSessionId());
                //因為找到客服所以更新在線"客戶"
                userSessionMap.put(userInfo.getSessionId(), userInfo);

               System.out.println("客服"+ serverInfo.getSessionId() + "正在為用戶" + userInfo.getSessionId() + "服務");

                Map<String, String> msg = new HashMap<>();
                //客服顯示在為誰服務
                msg.put("msg", "正在為用戶"+userInfo.getSessionId()+"服務！");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));
                msg.put("msg", "-----------------------------------------");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));
                //客戶顯示誰在為他服務
                msg.put("msg", "客服"+serverInfo.getSessionId()+"正在為您服務！");
                sendMsg(userInfo.getSession(), JSON.toJSONString(msg));
                msg.put("msg", "-----------------------------------------");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));
            }
            //無論是否有連接會員都放進"在線客服",並提示客服上線成功
            serverSessionMap.put(session.getId(), serverInfo);
            System.out.println("客服：" + serverInfo.getSessionId() + "連線成功,目前客服數量:" + serverSessionMap.size());
        }


        //當客戶進入聊天室
        if (role.equals(1)) {
            //創建一個客戶身分
            SocketUserInfo userInfo = new SocketUserInfo();
            userInfo.setSessionId(session.getId());
            userInfo.setSession(session);
            userInfo.setUserRole("客戶");

            //告訴客戶連線狀態為成功
            state.put("state", "success");

            //查詢是否有閒置客服
            //有:進入 無:不進入
            if (findFreeServer() != null){
                //透過SessionID找到閒置的客服
                SocketUserInfo serverInfo = serverSessionMap.get(findFreeServer());
                //將客服的目標會話id綁定(客服綁定客戶)
                serverInfo.setTargetSessionId(userInfo.getSessionId());
                //更新該客服狀態(有綁定目標)
                serverSessionMap.put(serverInfo.getSessionId(), serverInfo);
                //將客戶的目標會話id綁定(客戶綁定客服)
                userInfo.setTargetSessionId(serverInfo.getSessionId());
                System.out.println("客服"+ serverInfo.getSessionId() + "正在為" + userInfo.getSessionId()+"服務");

                Map<String, String> msg = new HashMap<>();
                //客服顯示為誰服務
                msg.put("msg", "正在為客戶"+userInfo.getSessionId()+"服務！");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));
                msg.put("msg", "----------------------------------------");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));
                //客戶顯示誰在服務
                msg.put("msg", "客服" + serverInfo.getSessionId() + "正在為您服務！");
                sendMsg(userInfo.getSession(), JSON.toJSONString(msg));

            } else {
                state.put("msg", "系统繁忙！");
            }

            //不管客戶是否有找到客服都要把它放進已上線的客戶
            userSessionMap.put(session.getId(), userInfo);
            System.out.println("用戶編號：" + userInfo.getSessionId() + "伺服器在線用戶數量為:" + userSessionMap.size());
        }
        //告訴客人訊息
        String result = JSON.toJSONString(state);
        System.out.println(result);
        sendMsg(session, result);
    }

    @OnClose
    public void onClose(Session session) {
        //取得客服資訊
        SocketUserInfo serverInfo = serverSessionMap.get(session.getId());
        //檢查客服資訊是否不為空
        if (serverInfo != null) {
            //將該客服從在線客服的map中移除
            serverSessionMap.remove(session.getId());
            //檢查該客服是否有正在服務的用戶
            if (null != serverInfo.getTargetSessionId()){
                //向該用戶發送訊息
                Map<String, String> result = new HashMap<>();
                result.put("msg", "系統錯誤,請重新整理！");
                sendMsg(userSessionMap.get(serverInfo.getTargetSessionId()).getSession(), JSON.toJSONString(result));
            }
            System.out.println("客服編號：" + serverInfo.getSessionId() + "退出連線,目前客服上線數量:" + serverSessionMap.size());
        } else {
            //將該用戶從在線用戶的map中移除
            userSessionMap.remove(session.getId());
            //從客服中解除該用戶的綁定
            for (SocketUserInfo serverSocketInfo: serverSessionMap.values()) {
                //查找綁定的客服，即客服綁定的用戶不為空，並且綁定的用戶id與現在下線的用戶id一致
                if (serverSocketInfo.getTargetSessionId() != null && serverSocketInfo.getTargetSessionId().equals(session.getId())){
                    //解除綁定
                    serverSocketInfo.setTargetSessionId(null);
                    serverSessionMap.put(serverSocketInfo.getSessionId(), serverSocketInfo);
                    System.out.println("用戶編號：" + session.getId() + "斷開了與客服" + serverSocketInfo.getSessionId() + "的連接");

                    //客服解除綁定後，可能還會有在線排隊的用戶，就讓這個客服去服務
                    String lineUser = findLineUser();
                    if (lineUser != null){
                        //將用戶綁定到該客服
                        serverSocketInfo.setTargetSessionId(lineUser);
                        serverSessionMap.put(serverSocketInfo.getSessionId(), serverSocketInfo);
                        //將該客服綁定到用戶
                        userSessionMap.get(lineUser).setTargetSessionId(serverSocketInfo.getSessionId());
                        System.out.println("客服"+ serverSocketInfo.getSessionId() + "正在為" + lineUser+"服務");

                        Map<String, String> result = new HashMap<>();
                        //客服顯示用戶資訊
                        result.put("msg", "正在為用戶"+lineUser+"服務！");
                        sendMsg(serverSocketInfo.getSession(), JSON.toJSONString(result));
                        //用戶顯示客服資訊
                        result.put("msg", "客服"+serverSocketInfo.getSessionId()+"正在為您服務！");
                        sendMsg(userSessionMap.get(lineUser).getSession(), JSON.toJSONString(result));
                    }
                }
            }
            System.out.println("用戶編號：" + session.getId() + "退出了連接，當前在線用戶共計：" + userSessionMap.size());
        }
    }


    // 定義一個方法處理用戶和客服端互相傳遞的消息
    @OnMessage
    public void onMessage(String message, Session session) {
        // 創建一個映射來存儲消息
        Map<String, String> result = new HashMap<>();

        // 從在線客服的映射中獲取當前會話的客服資訊
        SocketUserInfo serverInfo = serverSessionMap.get(session.getId());

        // 如果客服資訊不為空，則處理客服的消息
        if (serverInfo != null) {
            // 打印客服發送的消息和目標用戶的ID
            System.out.println("客服"+ session.getId()+"發送消息：\""+ message +"\"給用戶"+serverSessionMap.get(session.getId()).getTargetSessionId());

            // 將客服的ID和消息添加到結果映射中
            result.put("msg", "客服"+session.getId()+"："+message);

            // 判斷客服是否有服務的用戶，如果有，則將消息發送給該用戶
            if (null != serverSessionMap.get(session.getId()).getTargetSessionId()){
                sendMsg(userSessionMap.get(serverSessionMap.get(session.getId()).getTargetSessionId()).getSession(), JSON.toJSONString(result));
            }

        } else { // 如果客服資訊為空，則處理用戶的消息
            // 打印用戶發送的消息和目標客服的ID
            System.out.println("用戶"+ session.getId()+"發送消息：\""+ message +"\"給客服"+userSessionMap.get(session.getId()).getTargetSessionId());

            // 將用戶的ID和消息添加到結果映射中
            result.put("msg", "用戶"+session.getId()+"："+message);

            // 判斷用戶是否有服務的客服，如果有，則將消息發送給該客服
            if (null != userSessionMap.get(session.getId()).getTargetSessionId()){
                sendMsg(serverSessionMap.get(userSessionMap.get(session.getId()).getTargetSessionId()).getSession(), JSON.toJSONString(result));
            }
        }
    }

    //異常處理
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("異常!");
        throwable.printStackTrace();
    }

    // 定義一個同步方法，用於向指定的會話發送消息
    private synchronized void sendMsg(Session session, String msg) {
        try {
            // 使用基本的遠端接口發送文本消息
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            // 捕捉並打印任何IO異常
            e.printStackTrace();
        }
    }

    // 定義一個同步方法，用於尋找閒置的用戶
    private synchronized String findLineUser(){
        // 檢查在線用戶的map是否有元素
        if (userSessionMap.size() > 0){
            // 遍歷在線用戶的map
            for (SocketUserInfo UserInfo: userSessionMap.values()) {
                // 檢查用戶是否沒有被客服服務，即目標會話ID是否為空
                if (null == UserInfo.getTargetSessionId()){
                    // 如果是，返回該用戶的會話ID
                    return UserInfo.getSessionId();
                }
            }
        }
        // 如果沒有找到閒置的用戶，返回null
        return null;
    }

    // 定義一個同步方法，用於尋找閒置的客服
    private synchronized String findFreeServer(){
        // 檢查在線客服的map是否有元素
        if (serverSessionMap.size() > 0){
            // 遍歷在線客服的map
            for (SocketUserInfo serverInfo: serverSessionMap.values()) {
                // 檢查客服是否沒有在服務用戶，即目標會話ID是否為空
                if (null == serverInfo.getTargetSessionId()){
                    // 如果是，返回該客服的會話ID
                    return serverInfo.getSessionId();
                }
            }
        }
        // 如果沒有找到閒置的客服，返回null
        return null;
    }
}
