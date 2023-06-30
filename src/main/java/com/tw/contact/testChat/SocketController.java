//package com.tw.contact.testChat;
//
//import com.alibaba.fastjson.JSON;
//import jakarta.websocket.*;
//import jakarta.websocket.server.PathParam;
//import jakarta.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
////参数role判断用户角色0是客服，1是用户
//@ServerEndpoint(value = "/websocket/{role}")
//@Component
//public class SocketController {
//
//    //用本地线程保存session
//    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();
//    //保存所有连接上的用户的session
//    private static Map<String, SocketUserInfo> userSessionMap = new ConcurrentHashMap<>();
//    //保存在线客服的session
//    private static Map<String, SocketUserInfo> serverSessionMap = new ConcurrentHashMap<>();
//
//    //連線
//    @OnOpen
//    public void onOpen(Session session, @PathParam(value="role") Integer role) {
//
//        //設定狀態map
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("state", "error");
//
//        //保证各个线程里的变量相对独立于其他线程内的变量
//        sessions.set(session);
//
//        //客服上線的情況
//        if (role.equals(0)) {
//            //建立一個客服資訊
//            SocketUserInfo serverInfo = new SocketUserInfo();
//            serverInfo.setSessionId(session.getId());
//            serverInfo.setSession(session);
//            serverInfo.setUserRole("客服");
//
//            //客服連線狀態為成功
//            resultMap.put("state", "success");
//
//            //查詢是否有客戶正在排隊
//            if (findLineUser() != null){
//                //透過會話id取得客戶資訊
//                SocketUserInfo userInfo = userSessionMap.get(findLineUser());
//                //將客戶的目標會話id綁定(將客戶綁定客服)
//                userInfo.setTargetSessionId(serverInfo.getSessionId());
//                //將客服的目標會話id綁定(將客服綁定客戶)
//                serverInfo.setTargetSessionId(userInfo.getSessionId());
//                //存入正在聊天的客戶
//                userSessionMap.put(userInfo.getSessionId(), userInfo);
//
//               System.out.println("客服"+ serverInfo.getSessionId() + "正在為用戶" + userInfo.getSessionId() + "服務");
//
//                Map<String, String> result = new HashMap<>();
//                //客服显示用户信息
//                result.put("msg", "正在为用户"+userInfo.getSessionId()+"服务！");
//                sendMsg(serverInfo.getSession(), JSON.toJSONString(result));
//                //告诉用户有客服为他服务
//                result.put("msg", "客服"+serverInfo.getSessionId()+"正在为您服务！");
//                sendMsg(userInfo.getSession(), JSON.toJSONString(result));
//            }
//            //存入正在聊天的客服
//            serverSessionMap.put(session.getId(), serverInfo);
//            System.out.println("客服：" + serverInfo.getSessionId() + "連線成功,目前客服數量:" + serverSessionMap.size());
//        }
//        if (role.equals(1)) {
//
//            //创建一个在线用户信息
//            SocketUserInfo userInfo = new SocketUserInfo();
//            userInfo.setSessionId(session.getId());
//            userInfo.setSession(session);
//            userInfo.setUserRole("用户");
//
//            //告诉用户连接成功
//            resultMap.put("state", "success");
//
//            //去查询是否有在线的客服
//            //有空闲客服就将用户和客服绑定
//            if (findFreeServer() != null){
//                SocketUserInfo serverInfo = serverSessionMap.get(findFreeServer());
//                //将用户绑定到客服
//                serverInfo.setTargetSessionId(userInfo.getSessionId());
//                serverSessionMap.put(serverInfo.getSessionId(), serverInfo);
//                //将客服绑定到用户
//                userInfo.setTargetSessionId(serverInfo.getSessionId());
//                System.out.println("客户"+ serverInfo.getSessionId() + "正在为" + userInfo.getSessionId()+"服务");
//
//                Map<String, String> result = new HashMap<>();
//                //客服显示用户信息
//                result.put("msg", "正在为用户"+userInfo.getSessionId()+"服务！");
//                sendMsg(serverInfo.getSession(), JSON.toJSONString(result));
//                result.put("msg", "客服"+serverInfo.getSessionId()+"正在为您服务！");
//                sendMsg(userInfo.getSession(), JSON.toJSONString(result));
//            } else {
//                //告诉用户系统繁忙
//                resultMap.put("msg", "系统繁忙！");
//            }
//
//            //将在线用户信息保存到map中
//            userSessionMap.put(session.getId(), userInfo);
//            System.out.println("用户编号：" + userInfo.getSessionId() + "连接上服务器，当前在线用户共计：" + userSessionMap.size());
//        }
//        //返回连接信息
//        String result = JSON.toJSONString(resultMap);
//        System.out.println(result);
//        sendMsg(session, result);
//    }
//
//    //关闭连接
//    @OnClose
//    public void onClose(Session session) {
//        SocketUserInfo serverInfo = serverSessionMap.get(session.getId());
//        //客服下线
//        if (serverInfo != null) {
//            //将客户从map中移除
//            serverSessionMap.remove(session.getId());
//
//            //查看是否有服务服务对象
//            if (null != serverInfo.getTargetSessionId()){
//                //给用户说系统错误
//                Map<String, String> result = new HashMap<>();
//                result.put("msg", "系统错误，请刷新重试！");
//                sendMsg(userSessionMap.get(serverInfo.getTargetSessionId()).getSession(), JSON.toJSONString(result));
//            }
//            System.out.println("客服编号：" + serverInfo.getSessionId() + "退出了连接，当前在线客服共计：" + serverSessionMap.size());
//        } else {//用户下线
//            //将用户从map中移除
//            userSessionMap.remove(session.getId());
//
//            //从客服中解绑
//            for (SocketUserInfo serverSocketInfo: serverSessionMap.values()) {
//                //查找绑定的客服，即客服绑定的用户不为空，并且绑定的用户id和现在下线的用户id一样
//                if (serverSocketInfo.getTargetSessionId() != null && serverSocketInfo.getTargetSessionId().equals(session.getId())){
//                    //解绑
//                    serverSocketInfo.setTargetSessionId(null);
//                    serverSessionMap.put(serverSocketInfo.getSessionId(), serverSocketInfo);
//                    System.out.println("用户编号：" + session.getId() + "断开了与客服" + serverSocketInfo.getSessionId() + "的连接");
//
//                    //客服解绑以后，可能还会有在线排队的用户，就让这个客服去
//                    String lineUser = findLineUser();
//                    if (lineUser != null){
//                        //将用户绑定到客服
//                        serverSocketInfo.setTargetSessionId(lineUser);
//                        serverSessionMap.put(serverSocketInfo.getSessionId(), serverSocketInfo);
//                        //将客服绑定到用户
//                        userSessionMap.get(lineUser).setTargetSessionId(serverSocketInfo.getSessionId());
//                        System.out.println("客户"+ serverSocketInfo.getSessionId() + "正在为" + lineUser+"服务");
//
//                        Map<String, String> result = new HashMap<>();
//                        //客服显示用户信息
//                        result.put("msg", "正在为用户"+lineUser+"服务！");
//                        sendMsg(serverSocketInfo.getSession(), JSON.toJSONString(result));
//                        //用户显示客户信息
//                        result.put("msg", "客服"+serverSocketInfo.getSessionId()+"正在为您服务！");
//                        sendMsg(userSessionMap.get(lineUser).getSession(), JSON.toJSONString(result));
//                    }
//                }
//            }
//            System.out.println("用户编号：" + session.getId() + "退出了连接，当前在线用户共计：" + userSessionMap.size());
//        }
//    }
//
//    //用户和客户端互相传递消息
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        //消息
//        Map<String, String> result = new HashMap<>();
//
//        SocketUserInfo serverInfo = serverSessionMap.get(session.getId());
//        //客服消息
//        if (serverInfo != null) {
//            System.out.println("客服"+ session.getId()+"发送消息：\""+ message +"\"给用户"+serverSessionMap.get(session.getId()).getTargetSessionId());
//            result.put("msg", "客服"+session.getId()+"："+message);
//            //将消息发送给用户
//            //要判断是否绑定到有用户如果有就将消息传递到用户
//            if (null != serverSessionMap.get(session.getId()).getTargetSessionId()){
//                sendMsg(userSessionMap.get(serverSessionMap.get(session.getId()).getTargetSessionId()).getSession(), JSON.toJSONString(result));
//            } else {//如果没有就将消息给自己，嘻嘻嘻
//                sendMsg(session, JSON.toJSONString(result));
//            }
//
//        } else {//用户消息
//            System.out.println("用户"+ session.getId()+"发送消息：\""+ message +"\"给客户"+userSessionMap.get(session.getId()).getTargetSessionId());
//            result.put("msg", "用户"+session.getId()+"："+message);
//            //将消息发送给客服
//            //判断是否绑定了客服，如果有就发送消息
//            if (null != userSessionMap.get(session.getId()).getTargetSessionId()){
//                sendMsg(serverSessionMap.get(userSessionMap.get(session.getId()).getTargetSessionId()).getSession(), JSON.toJSONString(result));
//            } else{//同上
//                sendMsg(session,JSON.toJSONString(result));
//            }
//        }
//    }
//
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.out.println("異常!");
//        throwable.printStackTrace();
//    }
//
//
//
//    //统一的发送消息方法
//    private synchronized void sendMsg(Session session, String msg) {
//        try {
//            session.getBasicRemote().sendText(msg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    尋找沒有配對的客戶
//    private synchronized String findLineUser(){
////        線上的客戶數量
//        if (userSessionMap.size() > 0){
//            for (SocketUserInfo UserInfo: userSessionMap.values()) {
//                if (null == UserInfo.getTargetSessionId()){
//                    return UserInfo.getSessionId();
//                }
//            }
//        }
//        return null;
//    }
//
//    private synchronized String findFreeServer(){
//        if (serverSessionMap.size() > 0){
//            for (SocketUserInfo serverInfo: serverSessionMap.values()) {
//                if (null == serverInfo.getTargetSessionId()){
//                    return serverInfo.getSessionId();
//                }
//            }
//        }
//        return null;
//    }
//}
//
