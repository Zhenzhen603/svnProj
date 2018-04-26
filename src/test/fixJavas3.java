package test;

import java.sql.*;

/**
 * Created by ZhenZhen on 2018/4/21.
 */
public class fixJavas3 {

    public static Connection conn=getDatabaseConn();
    public static void main(String[] args) throws Exception {


        for (int i = 2; i < 3; i++) {

            //10年之前需要手动打
            String year="18";
            String DBdate = year + "-" + i;
            int addLine = 0;
            int reduceLine = 0;
            Statement stmt01 = conn.createStatement();
            String sql01 = "select DISTINCT revision from actions_original where changed_date like ?";
            PreparedStatement pstmt01 = conn.prepareStatement(sql01);
            String a = DBdate;
            pstmt01.setString(1, a + "%");
            ResultSet rs01 = pstmt01.executeQuery();
            while (rs01.next()) {
                int revision = rs01.getInt(1);
                //
                Statement stmt02 = conn.createStatement();
                String sql02 = "select revision,file_name from actions_original where revision=" + revision;
                ResultSet rs02 = stmt01.executeQuery(sql02);
                while (rs02.next()) {
                    String message = rs02.getString(2);
                    if (message.indexOf(".java") != -1) {
                        addLine++;
                    }

                }
            }
            System.out.println("add="+addLine+",reduce="+reduceLine);
            System.out.println(year + "年" + i + "月完成");
        }








        conn.close();
    }









    public static Connection getDatabaseConn(){
        //连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/svnshop?useSSL=false";
            String DB_user="root";
            String DB_password="root";
            Connection conn= DriverManager.getConnection(url, DB_user, DB_password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败");
            return null;
        }


    }
}
