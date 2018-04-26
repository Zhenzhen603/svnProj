package test;

import java.sql.*;

/**
 * Created by ZhenZhen on 2018/4/20.
 */
public class AddImport3 {



    public static Connection conn=getDatabaseConn();
    public static void main(String[] args) throws Exception {


        for (int i = 2; i < 3; i++) {

            //10年之前需要手动打
            String year="18";
            String DBdate = year + "-" + i;
            int addLine = 0;
            int reduceLine = 0;
            Statement stmt01 = conn.createStatement();
            String sql01 = "select DISTINCT revision from actions where changed_date like ?";
            PreparedStatement pstmt01 = conn.prepareStatement(sql01);
            String a = DBdate;
            pstmt01.setString(1, a + "%");
            ResultSet rs01 = pstmt01.executeQuery();
            while (rs01.next()) {
                int revision = rs01.getInt(1);
                //
                Statement stmt02 = conn.createStatement();
                String sql02 = "select hank_id from hanks where rM=" + revision;
                ResultSet rs02 = stmt01.executeQuery(sql02);
                while (rs02.next()) {
                    int hank_id = rs02.getInt(1);
//

                    String str03 = "select hank_id,changed from hanks where hank_id=" + hank_id;
                    Statement stmt03 = conn.createStatement();
                    ResultSet rs03 = stmt03.executeQuery(str03);
                    while (rs03.next()) {
                        // int hank_id=rs03.getInt(1);
                        String s = rs03.getString(2);

                        String[] ss = s.split("\r\n");
                        for (String s2 : ss) {
                            if (s2.length() < 1) {
                                continue;
                            }
                            //定义方法函数的格式
                            String fx01 = "import";


                            if (s2.substring(0, 1).equals("+")) {

                                if (s2.indexOf(fx01) != -1) {
                                    addLine++;
                                }

                            } else if (s2.substring(0, 1).equals("-")) {
                                if (s2.indexOf(fx01) != -1) {
                                    reduceLine++;
                                }
                            }
                        }

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
