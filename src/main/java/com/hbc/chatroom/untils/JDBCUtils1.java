package com.hbc.chatroom.untils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author: Beer
 * @Date: 2019/8/1 20:32
 * @Description: 基于DruidDataSoure
 */
public class JDBCUtils1 {
    private static DruidDataSource dataSource;

    static {
        Properties properties = Commutils.loadProperties("datasource.properties");

        try {
            //注册驱动
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.err.println("获取数据源失败");
        }
    }
    public static DruidPooledConnection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("获取连接失败");
        }
        return  null;
    }

    public static void closeResources(Connection connection, Statement statement) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResources(Connection connection,
                                      Statement statement,
                                      ResultSet resultSet) {
        closeResources(connection,statement);

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
