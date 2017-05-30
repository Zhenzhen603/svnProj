package test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by ZhenZhen on 2017/5/30.
 */
public class T02 {
    public static void main(String[] args) {

    }

    public static Connection getDatabaseConn(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/svnshop?useSSL=false";
            String DB_user="root";
            String DB_password="root";
            Connection conn= DriverManager.getConnection(url, DB_user, DB_password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
