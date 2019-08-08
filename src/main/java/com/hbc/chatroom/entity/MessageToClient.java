package com.hbc.chatroom.entity;


import lombok.Data;

import java.util.Map;

/**
 * @Author: Beer
 * @Date: 2019/8/8 16:52
 * @Description: 后端发送给前端的信息实体
 */

public class MessageToClient {
    //聊天的内容
    private String content;
    //服务端登录的所有用户列表
    private Map<String,String> names;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent (String userName,String msg) {
        this.content = userName + "说：" + msg;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }
}
