package test;

import net.sf.json.JSONArray;
import org.springframework.test.context.TestExecutionListeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 * Created by ZhenZhen on 2017/5/30.
 */
public class T02 {
    public static void main(String[] args) {
/*
        ArrayList<String> commit_dateList=new ArrayList<String>();ArrayList<Integer> addLineList=new ArrayList<Integer>();ArrayList<Integer> reduceLineList=new ArrayList<Integer>();
        ArrayList<String> changed_dateList=new ArrayList<String>();ArrayList<Integer> AcountsList=new ArrayList<Integer>();ArrayList<Integer> McountsList=new ArrayList<Integer>();ArrayList<Integer> DcountsList=new ArrayList<Integer>();



        System.out.println("{\"changed_dateList\":"+ JSONArray.fromObject(changed_dateList).toString()+",\"AcountsList\":"+JSONArray.fromObject(AcountsList).toString()+
                ",\"McountsList\":"+JSONArray.fromObject(McountsList).toString()+",\"DcountsList\":"+JSONArray.fromObject(AcountsList).toString()+",\"typeCounts\":["+0+","+0+","+0+","+0+","+0+"]," +
                "\"commit\":"+JSONArray.fromObject(commit_dateList).toString() +",\"addLine\":"+JSONArray.fromObject(addLineList).toString()+
                ",\"reduceLine\":"+JSONArray.fromObject(reduceLineList).toString()+"}");
    */
        for (int i = 0; i < 3; i++) {
            int a=0;
            a=a+2;
            System.out.println(a);
        }

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
