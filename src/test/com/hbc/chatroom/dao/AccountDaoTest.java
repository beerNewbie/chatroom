package com.hbc.chatroom.dao;

import com.hbc.chatroom.entity.User;
import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: Beer
 * @Date: 2019/8/4 15:54
 * @Description:
 */
public class AccountDaoTest {
    private AccountDao accountDao = new AccountDao();

    /**
     * 注册测试
     */
    @Test
    public void userRegister() {
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        boolean isSuccess = accountDao.userRegister(user);
        Assert.assertEquals(true,isSuccess);
    }

    /**
     * 登录测试
     */
    @Test
    public void userLogin() {
        User user = accountDao.userLogin("test",
                "test");
        System.out.println(user);
        Assert.assertNotNull(user);
    }


}