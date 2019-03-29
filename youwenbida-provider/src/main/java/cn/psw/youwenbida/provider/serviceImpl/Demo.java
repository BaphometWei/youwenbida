package cn.psw.youwenbida.provider.serviceImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/youwenbida?useSSL=false&&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "123456";
        //1.加载驱动
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //2.获取与数据库的链接
        Connection conn = DriverManager.getConnection(url, username, password);
        //3.获取用于向数据库发送sql语句的statement
        Statement st = conn.createStatement();
        //4.向数据库发sql,并获取代表结果集的resultset
        String sql = "SELECT * from answer";
        ResultSet rs = st.executeQuery(sql);
        //5.取出结果集的数据
        while(rs.next()){
            System.out.println("id=" + rs.getObject("aid"));
        }

        //6.关闭链接，释放资源
        rs.close();
        st.close();
        conn.close();

    }

}