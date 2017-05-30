package test;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import svn.utils.CreateHanks;
import svn.utils.GetData;

import java.sql.*;

/**
 * Created by ZhenZhen on 2017/5/28.
 */
public class T01 {

    public static void main(String[] args) throws SQLException {
       Connection conn=getDatabaseConn();
       int max=-1;
       int min_hankid=-1;
       int max_hankid=-1;
       if (max==-1){min_hankid=0;}
       else {
           String min_hankidstr="select hank_id from hanks where diff_id="+max;
           Statement stmt01=conn.createStatement();
           ResultSet rs01=stmt01.executeQuery(min_hankidstr);
           while (rs01.next()){
               min_hankid=rs01.getInt(1);
           }
           rs01.close();stmt01.close();
       }
       String max_hankidstr="select max(hank_id) from hanks ";
        Statement stmt02=conn.createStatement();
        ResultSet rs02=stmt02.executeQuery(max_hankidstr);
        while (rs02.next()){
            max_hankid=rs02.getInt(1);
        }
        rs02.close();stmt02.close();
       for (int i=min_hankid+1;i<=max_hankid;i++){
           String str03="select hank_id,changed from hanks where hank_id="+i;
           Statement stmt03=conn.createStatement();
           ResultSet rs03=stmt03.executeQuery(str03);
           while (rs03.next()){
               int hank_id=rs03.getInt(1);
               String s=rs03.getString(2);
               int addLine=0;
               int reduceLine=0;
               String [] ss=s.split("\r\n");
               for(String s2:ss){
                   if (s2.length()<1){continue;}
                   if (s2.substring(0,1).equals("+")){addLine++;}
                   else if(s2.substring(0,1).equals("-")){reduceLine++;}
               }
               String str04="update hanks set addLine="+addLine+",reduceLine="+reduceLine+" where hank_id="+hank_id;
               Statement stmt04=conn.createStatement();
               stmt04.execute(str04);
               stmt04.close();

           }
       }

    conn.close();
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
