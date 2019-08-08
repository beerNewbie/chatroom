package com.hbc.chatroom.entity;


import lombok.Data;

/**
 * @Author: Beer
 * @Date: 2019/8/8 16:46
 * @Description: 前端发给后端的信息
 *
 *  前端发来的信息格式
 *  群聊：{"msg":"hello","type":1}
 *  私聊：{"to":"0-","msg":"hi","type":2}
 */
public class MessageFromClient {
    //聊天信息
    private String msg;
    //聊天类别：1：群聊   2;私聊
    private String type;
    //私聊的对象SessionID
    private String to;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
