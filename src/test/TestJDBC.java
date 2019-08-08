import com.hbc.chatroom.untils.JDBCUtils;
import com.hbc.chatroom.untils.JDBCUtils1;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

/**
 * @Author: Beer
 * @Date: 2019/7/27 11:03
 * @Description:
 */
public class TestJDBC {
    @Test
    public void test00() {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "h12b34c56");
            //3.执行MySQL
            String sql = "select * from user;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            //遍历结果
            while (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                System.out.println("ID：" + id + "，用户名：" + username + "，密码：" + password);
            }
            //4.释放资源
            connection.close();
            statement.close();
            result.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 往MySQL中插入数据
     */
    @Test
    public void test01() {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "h12b34c56");
            //3.执行MySQL
            String sql = "insert into user(username, password)" +
                    "value('zs','456')";
            Statement statement = connection.createStatement();
            int resultRows =
                    statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
            //释放资源
            connection.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除语句
     */
    @Test
    public void test02() {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "h12b34c56");
            //3.执行SQL
            String sql = "delete from user where id = 1";
            Statement statement = connection.createStatement();
            int resultRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
            //4.释放资源
            connection.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Select
     */
    @Test
    public void test03() {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "h12b34c56");
            //3.执行SQL
            String userName = "zs 'or 1 = 1";
            String password = "hjjhkj";
            String sql = "select * from user where username = '" + userName + " " +
                    "and password = '" + password + "' ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            //遍历集合结果
            if (resultSet.next()) {
                System.out.println("Login Success!!!");
            } else {
                System.out.println("Login Fail!!!");
            }
            //释放资源
            connection.close();
            statement.close();
            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Safe Select
     */
    @Test
    public void test04() {
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "h12b34c56");
            //执行SQL
            String sql = "select * from user" + " where username = ? and password = ?";
            //预编译SQL
            PreparedStatement statement = connection.prepareStatement(sql);
            String userName = "zs or 1 = 1";
            String password = "hhdsjc";
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            //遍历结果
            if (resultSet.next()) {
                System.out.println("Login Success!!!");
            } else {
                System.out.println("Login Fail!!!");
            }
            //释放资源
            connection.close();
            statement.close();
            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   //============================================


    @Test
    public void test() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from user";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println("id：" + id + "，username：" + userName + ",password：" + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, statement, resultSet);
        }
    }

    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBCUtils1.getConnection();
            String sql = "select * from user where id = ? and username = ?";
            preparedStatement = connection.prepareStatement(sql);
            //第一个？下标就是1
            preparedStatement.setInt(1,7);
            preparedStatement.setString(2,"Java");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println("id：" + id + "，username：" + userName + ",password：" + password);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResources(connection,preparedStatement,resultSet);
        }
    }

    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into user(username,password) values (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"Java");
            preparedStatement.setString(2,DigestUtils.md5Hex("123"));
            int i = preparedStatement.executeUpdate();
            Assert.assertEquals(1,i);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResources(connection,preparedStatement);
        }
    }
}
