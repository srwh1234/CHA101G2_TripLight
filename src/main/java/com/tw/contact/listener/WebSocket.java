//package com.tw.contact.listener;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.websocket.*;
//import jakarta.websocket.server.PathParam;
//import jakarta.websocket.server.ServerEndpoint;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@ServerEndpoint("/chat/{username}")
//@Component
//@Slf4j
//public class WebSocket {
//    //注入dao或者service，注意：因为dao层接口和service层都是单例的Bean
//    //webSocket 不是单例的，所以在注入dao或者service时，需要用set方法对其进行注入，保证每一个都是独立的
//    private static ChatMapper chatMapper;
//    //参数中的ChatMapper  是 单例池中的ChatMapper
//    @Autowired
//    public void setChatMapper(ChatMapper chatMapperBean){
//        WebSocket.chatMapper = chatMapperBean;
//    }
//
//    //当前连接数
//    private static int onLinePersonNum;
//    //定义为Map结构，key值代表用户名称或其他唯一标识，Value代表对应的WebSocket连接。
//    //ConcurrentHashMap 保证线程安全，用来存放每个客户端对应的WebSocket对象
//    private static Map<String,WebSocket> webSocketMap = new ConcurrentHashMap<String, WebSocket>();
//    //用户名
//    private String username;
//    //当前httpSession
//    private Session session;
//
//    /**
//     * 打开链接
//     * @param username
//     * @param session
//     */
//    @OnOpen
//    public void openConnect(@PathParam("username")String username, Session session){
//        this.session = session;
//        this.username = username;
//        //在线连接数+1
//        onlinePerNumAdd();
//        //用户名和当前用户WebSocket对象放进Map中
//        webSocketMap.put(this.username,this);
//        log.info("{}连接服务器成功。。。。",this.username);
//    }
//
//    /**
//     * 关闭连接
//     * @param username
//     * @param session
//     * @PathParam 用来获取路径中的动态参数Key值
//     */
//    @OnClose
//    public void closeConnect(@PathParam("username")String username, Session session){
//        webSocketMap.remove(username);
//        //在线连接数-1
//        onlinePerNumSub();
//        log.info("{} 断开连接。。。",username);
//    }
//
//    /**
//     * 错误提示
//     */
//    @OnError
//    public void errorConnect(Session session, Throwable error){
//        log.error("websocket连接异常：{}",error.getMessage());
//    }
//
//    @OnMessage
//    public void send(String message, Session session) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map map = objectMapper.readValue(message, Map.class);
//        sendMessage(map.get("username").toString(),message);
//    }
//
//    /**
//     * 点对点发送
//     * @param username
//     * @param message
//     * @throws IOException
//     */
//    private void sendMessage(String username,String message) throws IOException {
//        WebSocket webSocket = webSocketMap.get(username);
//        webSocket.session.getBasicRemote().sendText(message);
//    }
//    /**
//     * 广播类型发送
//     * @param message
//     * @throws IOException
//     */
//    private void sendMessage(String message) throws IOException {
//        Set<String> keys = webSocketMap.keySet();
//        for (String key : keys) {
//            WebSocket webSocket = webSocketMap.get(key);
//            webSocket.sendMessage(message);
//        }
//    }
//
//    private synchronized static void onlinePerNumAdd(){
//        WebSocket.onLinePersonNum ++;
//    }
//    private synchronized static void onlinePerNumSub(){
//        WebSocket.onLinePersonNum --;
//    }
//    private synchronized static int getOnLinePerNum(){
//        return WebSocket.onLinePersonNum;
//    }
//}
//