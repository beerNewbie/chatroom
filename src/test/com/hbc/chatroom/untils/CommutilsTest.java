package com.hbc.chatroom.untils;


import com.hbc.chatroom.entity.User;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @Author: Beer
 * @Date: 2019/8/1 19:32
 * @Description:
 */
public class CommutilsTest {

    @Test
    public void loadProperties() {
        String fileName = "db.properties";
        Properties properties = Commutils.loadProperties(fileName);
        String url = properties.getProperty("url");
        Assert.assertNotNull(url);
    }

    @Test
    public void gsonTest1() {
        User user = new User();
        user.setId(10);
        user.setUserName("C++");
        user.setPassword("789");
        String jsonStr = Commutils.objectToJson(user);
        System.out.println(jsonStr);
    }

    @Test
    public void gsonTest2() {
        String jsonStr = "{\"id\":10,\"userName\":\"C++\",\"password\":\"789\"}";
        User usere = (User) Commutils.jsonToObject(jsonStr,User.class);
        System.out.println(usere);

    }

}