package servlet;

import net.sf.json.JSONArray;
import org.apache.struts2.components.ElseIf;
import org.aspectj.weaver.patterns.Pointcut;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ZhenZhen on 2017/5/23.
 */
@WebServlet(name = "QueryAMDServlet")
public class QueryAMDServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String date01=request.getParameter("date").substring(2);
        String date=null;
        if(date01.length()>4){
            int length=date01.substring(5).length();
            if (length==1){
                date=date01+" ";
            }
            else {
                date=date01;
            }}else {date=date01;}
        //链接数据库并查询对应日期的信息
        int Acounts=0;int Mcounts=0;int Dcounts=0;
        int javaCounts=0;int jspCounts=0;int htmlCounts=0;int xmlCounts=0;int other=0;
        System.out.println("date="+date);
        ArrayList<String> commit_dateList=new ArrayList<String>();ArrayList<Integer> addLineList=new ArrayList<Integer>();ArrayList<Integer> reduceLineList=new ArrayList<Integer>();
        try {
            Connection conn=getDatabaseConn();
            String sql="select * from actions_original where changed_date like '"+date+"%'";
            Statement  stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                String AMDType=rs.getString(6);
                String fileName=rs.getString(7);
                String fileType=fileName.substring(fileName.lastIndexOf(".")+1);
                if (AMDType.equals("A")){
                    Acounts++;
                }
                else if (AMDType.equals("M")){
                    Mcounts++;
                }
                else if(AMDType.equals("D")){
                    Dcounts++;
                }
                if(fileType.equals("java")){javaCounts++;}
                else if(fileType.equals("jsp")){jspCounts++;}
                else if (fileType.equals("html")){htmlCounts++;}
                else if(fileType.equals("xml")){xmlCounts++;}
                else{other++;}

            }
            rs.close();stmt.close();

            //获取影响的代码行数  select revision.commit_date,addLine,reduceLine from hanks,revision where revision.commit_date like '17%' and revision.revision=hanks.rM;
            String sql02="select revision,commit_date from revision where commit_date like '"+date+"%' order by revision";
            Statement stmt02=conn.createStatement();
            ResultSet rs02=stmt02.executeQuery(sql02);
            while(rs02.next()){
                String commit=rs02.getString(2);
                commit_dateList.add(commit);
                int revision=rs02.getInt(1);
                String sql03="select addLine,reduceLine from hanks where rM="+revision;
                Statement stmt03=conn.createStatement();
                ResultSet rs03=stmt03.executeQuery(sql03);
                int add=0;int reduce=0;
                while(rs03.next()){
                    int add03=rs03.getInt(1);
                    add=add+add03;
                    int reduce03=rs03.getInt(2);
                    reduce=reduce+reduce03;
                }
                addLineList.add(add);
                reduceLineList.add(reduce);
                rs03.close();stmt03.close();
            }
            rs02.close();stmt02.close();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(commit_dateList.size()+"  "+addLineList.size()+"  "+reduceLineList.size());
        out.println("{\"counts\":["+Acounts+","+Mcounts+","+Dcounts+"],\"typeCounts\":["+javaCounts+","+jspCounts+","+htmlCounts+","+xmlCounts+","+other+"]," +
                    "\"commit\":"+JSONArray.fromObject(commit_dateList).toString() +",\"addLine\":"+JSONArray.fromObject(addLineList).toString()+
                    ",\"reduceLine\":"+JSONArray.fromObject(reduceLineList).toString()+"}");


        //   System.out.println("servlet执行完成----"+"{\"counts\":["+Acounts+","+Mcounts+","+Dcounts+"]}"+"java"+javaCounts+",jsp"+jspCounts+",html"+htmlCounts+",xml"+xmlCounts+",other"+other);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

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
