package cn.com.mebatis.v1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.mebatis.v1.mapper.Blog;

/**
 * 执行器
 * 
 * @author gaopengchao 2019年5月6日
 */
public class MyExecutor
{

    public <T> T query(String sql, Object paramater)
    {
        Connection conn = null;
        Statement stmt = null;
        Blog blog = new Blog();
        try
        {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开连接
            conn = DriverManager.getConnection("jdbc:mysql://192.168.16.239:3306/cms-2019", "root", "root");
            // 执行查询
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(sql, paramater));
            // 获取结果集
            while (rs.next())
            {
                Integer bid = rs.getInt("bid");
                String name = rs.getString("name");
                Integer authorId = rs.getInt("author_id");
                blog.setAuthorId(authorId);
                blog.setBid(bid);
                blog.setName(name);
            }
            System.out.println(blog);

            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null)
                    stmt.close();
            }
            catch(SQLException se2)
            {
            }
            try
            {
                if (conn != null)
                    conn.close();
            }
            catch(SQLException se)
            {
                se.printStackTrace();
            }
        }
        return (T) blog;
    }
}
