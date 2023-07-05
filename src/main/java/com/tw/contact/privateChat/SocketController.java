package com.tw.contact.privateChat;


import com.alibaba.fastjson.JSON;
import com.tw.employee.dao.EmployeeRepository;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//参数role判断用户角色0是客服，1是用户
@ServerEndpoint(value = "/websocket/{role}/{userId}")
@Component
public class SocketController {

    //用本地執行緒保存session
    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();
    //保存上線用戶 String放SessionID
    private static Map<String, SocketUserInfo> userSessionMap = new ConcurrentHashMap<>();
    //保存上線客服 String放SessionID
    private static Map<String, SocketUserInfo> serverSessionMap = new ConcurrentHashMap<>();
    String chatRoomId = UUID.randomUUID().toString();

    //連線設定
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "role") int role, @PathParam(value = "userId") int userId) {
        //用來儲存訊息
        Map<String, String> msg = new HashMap<>();

        //執行緒獨立
        sessions.set(session);

        //客服上線的情況
        if (role == 0) {
            //建立一個客服資訊
            EmployeeRepository employeeRepository = SpringApplicationContext.getBean(EmployeeRepository.class);
            SocketUserInfo serverInfo = new SocketUserInfo();
            serverInfo.setName(employeeRepository.findByEmployeeId(userId).getEmployeeName());
            serverInfo.setSessionId(session.getId());
            serverInfo.setSession(session);
            serverInfo.setUserRole("客服");

            //查詢是否有用戶正在排隊?
            //有:進入 無:不進入
            if (findLineUser() != null) {
                //透過會話id取得用戶資訊
                SocketUserInfo userInfo = userSessionMap.get(findLineUser());
                //將用戶的目標會話id綁定(將用戶綁定客服)
                userInfo.setTargetSessionId(serverInfo.getSessionId());
                //將客服的目標會話id綁定(將客服綁定用戶)
                serverInfo.setTargetSessionId(userInfo.getSessionId());
                //因為找到客服所以更新在線"用戶"
                userSessionMap.put(userInfo.getSessionId(), userInfo);

                //客服顯示在為誰服務
                msg.put("msg", "正在為用戶" + userInfo.getSessionId() + "服務！");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));

                //用戶顯示誰在為他服務
                msg.put("msg", "客服" + serverInfo.getName() + "正在為您服務！");
                sendMsg(userInfo.getSession(), JSON.toJSONString(msg));

            }
            //無論是否有連接會員都放進"在線客服",並提示客服上線成功
            serverSessionMap.put(session.getId(), serverInfo);
        }


        //當用戶進入聊天室
        if (role == 1) {
            //創建一個用戶身分
            SocketUserInfo userInfo = new SocketUserInfo();
            userInfo.setSessionId(session.getId());
            userInfo.setSession(session);
            userInfo.setUserRole("用戶");

            //查詢是否有連線到閒置客服
            if (findFreeServer() != null) {
                //透過SessionID找到閒置的客服
                SocketUserInfo serverInfo = serverSessionMap.get(findFreeServer());
                //將客服的目標會話id綁定(客服綁定用戶)
                serverInfo.setTargetSessionId(userInfo.getSessionId());
                //更新該客服狀態(有綁定目標)
                serverSessionMap.put(serverInfo.getSessionId(), serverInfo);
                //將用戶的目標會話id綁定(用戶綁定客服)
                userInfo.setTargetSessionId(serverInfo.getSessionId());
                //System.out.println("客服"+ serverInfo.getSessionId() + "正在為" + userInfo.getSessionId()+"服務");

                //客服顯示為誰服務
                msg.put("msg", "正在為用戶" + userInfo.getSessionId() + "服務！");
                sendMsg(serverInfo.getSession(), JSON.toJSONString(msg));

                //用戶顯示誰在服務
                msg.put("msg", "客服人員:" + serverInfo.getName() + "正在為您服務！");
                sendMsg(userInfo.getSession(), JSON.toJSONString(msg));
            }
            //沒有連線到客服人員
            else {
                msg.put("msg", "目前客服人員忙線中！請繼續等待客服人員！");
                sendMsg(userInfo.getSession(), JSON.toJSONString(msg));
            }

            //不管用戶是否有找到客服都要把它放進已上線的用戶
            userSessionMap.put(session.getId(), userInfo);
            //System.out.println("用戶編號：" + userInfo.getSessionId() + "伺服器在線用戶數量為:" + userSessionMap.size());
        }
    }

    //關閉連線
    @OnClose
    public void onClose(@PathParam(value = "role") int role, Session session) {

        SocketUserInfo serverInfo = serverSessionMap.get(session.getId());
        Map<String, String> msg = new HashMap<>();
        //客服取消連線
        if (role == 0) {
            //將該客服從在線客服的map中移除
            serverSessionMap.remove(session.getId());
            //檢查該客服是否有正在服務的用戶
            if (null != serverInfo.getTargetSessionId()) {
                //向該用戶發送訊息
                msg.put("msg", "您的問題已解決！謝謝您的支持！");
                sendMsg(userSessionMap.get(serverInfo.getTargetSessionId()).getSession(), JSON.toJSONString(msg));
            }
        }
        //用戶取消連線
        else {
            //將該用戶從在線用戶的map中移除
            userSessionMap.remove(session.getId());

            //從客服中解除該用戶的綁定
            for (SocketUserInfo serverSocketInfo : serverSessionMap.values()) {

                msg.put("msg", "用戶的問題已得到解決！為您轉接其他用戶！");
                sendMsg(serverSocketInfo.getSession(), JSON.toJSONString(msg));

                //查找綁定的客服，即客服綁定的用戶不為空，並且綁定的用戶id與現在下線的用戶id一致
                if (serverSocketInfo.getTargetSessionId() != null && serverSocketInfo.getTargetSessionId().equals(session.getId())) {
                    //解除綁定
                    serverSocketInfo.setTargetSessionId(null);
                    serverSessionMap.put(serverSocketInfo.getSessionId(), serverSocketInfo);

                    //客服解除綁定後，可能還會有在線排隊的用戶，就讓這個客服去服務
                    String lineUser = findLineUser();
                    if (lineUser != null) {
                        //將用戶綁定到該客服
                        serverSocketInfo.setTargetSessionId(lineUser);
                        serverSessionMap.put(serverSocketInfo.getSessionId(), serverSocketInfo);
                        //將該客服綁定到用戶
                        userSessionMap.get(lineUser).setTargetSessionId(serverSocketInfo.getSessionId());

                        //客服顯示用戶資訊
                        msg.put("msg", "正在為用戶" + lineUser + "服務！");
                        sendMsg(serverSocketInfo.getSession(), JSON.toJSONString(msg));
                        //用戶顯示客服資訊
                        msg.put("msg", "客服" + serverSocketInfo.getName() + "正在為您服務！");
                        sendMsg(userSessionMap.get(lineUser).getSession(), JSON.toJSONString(msg));
                    }
                }
            }
        }
    }

    // 定義一個方法處理用戶和客服端互相傳遞的消息
    @OnMessage
    public void onMessage(String message,@PathParam(value = "role") int role, Session session) {
        // 創建一個映射來存儲消息
        Map<String, String> msg = new HashMap<>();

        //客服傳訊息給用戶
        if (role == 0) {
            // 從在線客服的映射中獲取當前會話的客服資訊
            SocketUserInfo serverInfo = serverSessionMap.get(session.getId());
            msg.put("msg", "客服人員" + serverInfo.getName() + "：" + message);
            // 判斷客服是否有服務的用戶，如果有，則將消息發送給該用戶
            if (null != serverSessionMap.get(session.getId()).getTargetSessionId()) {
                sendMsg(userSessionMap.get(serverInfo.getTargetSessionId()).getSession(), JSON.toJSONString(msg));
                ChatRecordRepository chatRecordRepository = SpringApplicationContext.getBean(ChatRecordRepository.class);
                ChatRecord chatRecord = new ChatRecord();
                chatRecord.setChatRoomId(chatRoomId);
//                chatRecord.setUser();
                chatRecord.setCustomerName(serverInfo.getName());
                chatRecord.setChatContent(JSON.toJSONString(msg));
                chatRecord.setChatTime(LocalDateTime.now());
                System.out.println(chatRecord);
                chatRecordRepository.save(chatRecord);
                System.out.println("成功存進資料庫!!");
            }
        }
        //處理用戶傳訊息給客服
        else {
            // 將用戶的ID和消息添加到結果映射中
            msg.put("msg", "用戶" + session.getId() + "：" + message);

            SocketUserInfo userInfo = userSessionMap.get(session.getId());
            // 從上線中用戶找到該用戶並找到他的目標id不為null時發送訊息
            if (userSessionMap.get(session.getId()).getTargetSessionId() != null) {
                sendMsg(serverSessionMap.get(userInfo.getTargetSessionId()).getSession(), JSON.toJSONString(msg));
            }
        }
    }

    //異常處理
    @OnError
    public void onError(Session session, Throwable throwable) {
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
    private synchronized String findLineUser() {
        // 檢查在線用戶的map是否有元素
        if (userSessionMap.size() > 0) {
            // 遍歷在線用戶的map
            for (SocketUserInfo UserInfo : userSessionMap.values()) {
                // 檢查用戶是否沒有被客服服務，即目標會話ID是否為空
                if (null == UserInfo.getTargetSessionId()) {
                    // 如果是，返回該用戶的會話ID
                    return UserInfo.getSessionId();
                }
            }
        }
        // 如果沒有找到閒置的用戶，返回null
        return null;
    }

    // 定義一個同步方法，用於尋找閒置的客服
    private synchronized String findFreeServer() {
        // 檢查在線客服的map是否有元素
        if (serverSessionMap.size() > 0) {
            // 遍歷在線客服的map
            for (SocketUserInfo serverInfo : serverSessionMap.values()) {
                // 檢查客服是否沒有在服務用戶，即目標會話ID是否為空
                if (null == serverInfo.getTargetSessionId()) {
                    // 如果是，返回該客服的會話ID
                    return serverInfo.getSessionId();
                }
            }
        }
        // 如果沒有找到閒置的客服，返回null
        return null;
    }
}
