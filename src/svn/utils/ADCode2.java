package svn.utils;

import com.mysql.jdbc.*;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ZhenZhen on 2018/4/19.
 */
public class ADCode2 {
    public static Connection conn=getDatabaseConn();
    public static void main(String[] args) throws Exception{


//
        //10年以后
        String year=null;
        for(int j=10;j<18;j++){
            year=String.valueOf(j);

            for (int i = 2; i <12 ; i++) {

                //10年之前需要手动打  String year= "09";
                String  DBdate=year+"-"+i;
                int addLine=0;
                int reduceLine=0;
                Statement stmt01=conn.createStatement();
                String sql01="select DISTINCT revision from actions where committer='markt' and changed_date like ?";
                PreparedStatement pstmt01 = conn.prepareStatement(sql01);
                String a=DBdate;
                pstmt01.setString(1, a+"%");
                ResultSet rs01=pstmt01.executeQuery();
                while (rs01.next()) {
                    int revision=rs01.getInt(1);
                    //
                    Statement stmt02=conn.createStatement();
                    String sql02="select hank_id from hanks where rM="+ revision;
                    ResultSet rs02=stmt01.executeQuery(sql02);
                    while (rs02.next()) {
                        int hank_id=rs02.getInt(1);
//

                        String str03="select hank_id,changed from hanks where hank_id="+hank_id;
                        Statement stmt03=conn.createStatement();
                        ResultSet rs03=stmt03.executeQuery(str03);
                        while (rs03.next()){
                            // int hank_id=rs03.getInt(1);
                            String s=rs03.getString(2);

                            String [] ss=s.split("\r\n");
                            for(String s2:ss){
                                if (s2.length()<1){continue;}
                                if (s2.substring(0,1).equals("+")){
                                    if (s2.length()>1){
                                        addLine++;
                                    }

                                }
                                else if(s2.substring(0,1).equals("-")){
                                    if (s2.length()>1) {
                                        reduceLine++;
                                    }
                                }
                            }

                        }
                    }
                }
                //这是一个月的数据,统计后插入数据库
                String sql03A="insert into ADCode values (?,?,?,?,?)";
                PreparedStatement psA=(PreparedStatement) conn.prepareStatement(sql03A);
                psA.clearBatch();
                psA.setInt(1, Integer.parseInt(year));
                psA.setInt(2,i);
                psA.setString(3, DBdate);
                psA.setInt(4, addLine);
                psA.setInt(5, reduceLine);
                psA.addBatch();
                psA.executeBatch();
                psA.close();
                System.out.println(year+"年"+i+"月完成");
            }

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
