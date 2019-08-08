package com.hbc.chatroom.dao;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.hbc.chatroom.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * @Author: Beer
 * @Date: 2019/8/3 9:59
 * @Description:
 */
public class AccountDao extends BaseDao {

    //用户登录
    public User userLogin(String userName, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        connection = getConnection();
        String sql = "select * from user where username = ?" +
                " and password = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserInfo(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("查询用户出错！！！");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement,resultSet);
        }
        return user;
    }

    public User getUserInfo(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    //用户注册
    public boolean userRegister(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;

        connection = getConnection();
        String sql = "insert into user(username,password)" +
                " value(?,?) ";
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            System.err.println("用户注册失败！！！");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement);
        }
        return isSuccess;

    }

}
