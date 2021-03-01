package com.xq.xtool.config;

import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket")
@Service
public class WebSocketService {
    // 第一次连接调用
    @OnOpen
    public void open(Session session) throws IOException {
        System.out.println("connect..");
        session.getBasicRemote().sendText("server: 登陆成功!");
    }

    // 关闭连接调用
    @OnClose
    public void close() {
        System.out.println("disconnect..");
    }

    // 接收消息
    @OnMessage
    public void message(String message, Session session) {
        System.out.println("client send: " + message);
    }

}
