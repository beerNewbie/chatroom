package com.hbc.chatroom.untils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: Beer
 * @Date: 2019/7/30 19:32
 * @Description: 封装了基础的工具方法，如加载配置文件、json序列化等
 *
 *      创建一个类的测试类：Ctrl+shift+T
 */
public class Commutils {
    private static final Gson gson = new GsonBuilder().create();
    public Commutils() {

    }

    /**
     * 根据指定的文件名加载配置文件
     * @param fileNme 配置文件名
     * @return
     */
    public static Properties loadProperties(String fileNme) {
        Properties properties = new Properties();

        try {
            //获取当前配置文件下的文件输入流
            InputStream in = Commutils.class.getClassLoader().getResourceAsStream(fileNme);
            //加载配置文件下的所有内容
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    //json序列化
    public static String objectToJson(Object obj) {
        return gson.toJson(obj);
    }

    //Json反序列化
    public static Object jsonToObject(String jsonStr, Class objClass) {
        return gson.fromJson(jsonStr,objClass);
    }

    /**
     * 判断输入的额用户名密码是否为空
     * @param str
     * @return
     */
    public static boolean strIsNull(String str) {
        return str == null || str.equals("");
    }
}
