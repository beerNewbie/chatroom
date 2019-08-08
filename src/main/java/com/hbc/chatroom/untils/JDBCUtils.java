package com.hbc.chatroom.untils;

import java.sql.*;
import java.util.Properties;

/**
 * @Author: Beer
 * @Date: 2019/7/30 19:54
 * @Description: JDBC操作的公共方法
 */
public class JDBCUtils {
  private static String driverName;
  private static String url;
  private static String userName;
  private static String password;

  static {
      Properties properties = Commutils.loadProperties("db.properties");
      driverName = properties.getProperty("driverName");
      url = properties.getProperty("url");
      userName = properties.getProperty("userName");
      password = properties.getProperty("password");
      //1.加载驱动
      try {
          Class.forName(driverName);
      } catch (ClassNotFoundException e) {
          System.err.println("加载驱动失败");
          e.printStackTrace();
      }
  }

  //获取数据库连接操作
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            System.out.println("获取数据库连接出错");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源
     * @param connection
     * @param statement
     */
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
