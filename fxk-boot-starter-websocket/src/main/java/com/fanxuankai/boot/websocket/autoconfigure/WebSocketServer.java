package com.fanxuankai.boot.websocket.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author fanxuankai
 */
@Component
@Service
@ServerEndpoint("/api/websocket/{sid}")
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    // 当前在线连接数
    private static int onlineCount = 0;
    // 存放每个客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        this.sid = sid;
        addOnlineCount();           //在线数加1
        try {
            sendMessage("conn_success");
            log.info("有新窗口开始监听:" + sid + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1

        log.info("释放的sid为：" + sid);

        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            try {
                //为null则全部推送
                if (sid == null) {
//                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}