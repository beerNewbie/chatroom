package com.hbc.chatroom.service;

import com.hbc.chatroom.entity.MessageFromClient;
import com.hbc.chatroom.entity.MessageToClient;
import com.hbc.chatroom.untils.Commutils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: Beer
 * @Date: 2019/8/4 19:40
 * @Description:
 */
@ServerEndpoint("/websocket")
public class WebSocket {
    //存储所有连接到后端的websocket
    //使用static使集合共享
    private static CopyOnWriteArraySet<WebSocket> clients =
            new CopyOnWriteArraySet<>();
    //缓存所有用户列表
    private static Map<String, String> names = new ConcurrentHashMap<>();
    //绑定当前websocket会话
    private Session session;
    //当前客户端的用户名
    private String userName;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String userName = session.getQueryString().split("=")[1];
        this.userName = userName;

        //将客户端实体保存到clients
        clients.add(this);
        //将当前用户以及SessionID保存到用户列表
        names.put(session.getId(), userName);
        System.out.println("加入新的连接，当前SessionId为：" + session.getId() +
                "，用户名为：" + userName);

        //发送给所有在线用户一个上线通知
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setContent(userName+"上线了。。。");
        messageToClient.setNames(names);
        //发送信息
        String jsonStr = Commutils.objectToJson(messageToClient);
        for (WebSocket webSocket : clients) {
            webSocket.sendMsg(jsonStr);
        }
    }

    @OnError
    public void onError(Throwable e) {
        System.err.println("websocket连接失败");
        e.printStackTrace();
    }

    @OnMessage
    public void onMessage(String msg) {
        //将msg反序列化为MessageFromClient
        MessageFromClient messageFromClient =
                (MessageFromClient) Commutils.jsonToObject(msg,MessageFromClient.class);
        if (messageFromClient.getType().equals("1")) {
            //群聊信息
            String content = messageFromClient.getMsg();

            MessageToClient messageToClient = new MessageToClient();
            messageToClient.setContent(content);
            messageToClient.setNames(names);

            //广播发送
            for (WebSocket webSocket : clients) {
                webSocket.sendMsg(Commutils.objectToJson(msg));
            }
        }else if (messageFromClient.getType().equals("2")) {
            String content = messageFromClient.getMsg();
            int toLen = messageFromClient.getTo().length();
            String tos[] =
                    messageFromClient.getTo().substring(0,toLen - 1).split("-");
            List<String> list = Arrays.asList(tos);

            //给指定的SessionID发送信息
            for (WebSocket webSocket : clients) {
                if (list.contains(webSocket.session.getId())
                        && this.session.getId() != webSocket.session.getId()) {
                    //发送私聊信息
                    MessageToClient messageToClient = new MessageToClient();
                    messageToClient.setContent(userName,content);
                    messageToClient.setNames(names);
                    webSocket.sendMsg(Commutils.objectToJson(messageToClient));


                }
            }


        }


    }

    @OnClose
    public void onClose() {
        clients.remove(this);
        //将当前用户以及SessionID保存到用户列表
        names.remove(session.getId());
        System.out.println("一位用户已下线，用户名为：" + userName);

        //发送给所有在线用户一个下线通知
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setContent(userName+"下线了。。。");
        messageToClient.setNames(names);
        //发送信息
        String jsonStr = Commutils.objectToJson(messageToClient);
        for (WebSocket webSocket : clients) {
            webSocket.sendMsg(jsonStr);
        }
    }

    public void sendMsg(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
