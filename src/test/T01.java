package test;

import svn.utils.CreateHanks;
import svn.utils.GetData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ZhenZhen on 2017/5/28.
 */
public class T01 {

    public static void main(String[] args) throws SQLException {
        Connection conn= getDatabaseConn();
        int max=-1;
        GetData.updateDatabase(-1,-1);

         //   FileDiff.filediff(svnurl, username, password,cRevAct,cRevCont);//diff_original表,cRevAct没有用到
            Statement stmt0A=conn.createStatement();
            stmt0A.clearBatch();
            String sql171="update diff_original,actions_original set diff_original.rMtype=actions_original.type where diff_original.file_path=actions_original.file_path and diff_original.rM=actions_original.revision and diff_original.diff_id >"+max;
            stmt0A.addBatch(sql171);stmt0A.executeBatch();stmt0A.clearBatch();
            String sql172= "update diff_original,actions_original set diff_original.rNtype=actions_original.type where diff_original.file_path=actions_original.file_path and diff_original.rN=actions_original.revision and diff_original.diff_id >" +max;
            stmt0A.addBatch(sql172);stmt0A.executeBatch();stmt0A.clearBatch();
            String sql17="insert into diff(diff_id,file_id,file_path,commit_date,rN,rNtype,rM,rMtype,diff)(select diff_id,file_id,file_path,commit_date,rN,rNtype,rM,rMtype,diff from diff_original where diff_id >"+max+" )";
            String sql18="update file,diff set diff.file_id=file.file_id where diff.file_path=file.file_path and diff.commit_date=file.file_date and diff.diff_id>"+max;
            stmt0A.addBatch(sql17);stmt0A.addBatch(sql18);stmt0A.executeBatch();stmt0A.clearBatch();
            stmt0A.close();

            CreateHanks.create(max);
        System.out.println("T01 finish");
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
