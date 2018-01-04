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
       // System.out.println("初始date："+request.getParameter("date"));
        String date01=request.getParameter("date").substring(2);
        String user=request.getParameter("userName");
        System.out.println(date01+user);
        String date=null;
        if(date01.length()>4){
            String index=date01.substring(4);
            if (index.equals("-")&date01.substring(5).length()>0){
                date=date01+" ";
            }
            else {
                date=date01;
            }}else {date=date01;}
       // System.out.println("处理后的date："+date);
        //初始化返回值
        int javaCounts=0;int jspCounts=0;int htmlCounts=0;int xmlCounts=0;int other=0;
        ArrayList<String> commit_dateList=new ArrayList<String>();ArrayList<Integer> addLineList=new ArrayList<Integer>();ArrayList<Integer> reduceLineList=new ArrayList<Integer>();
        ArrayList<String> changed_dateList=new ArrayList<String>();ArrayList<Integer> AcountsList=new ArrayList<Integer>();ArrayList<Integer> McountsList=new ArrayList<Integer>();ArrayList<Integer> DcountsList=new ArrayList<Integer>();
       //所有开发者汇总信息
        if(user.equals("All")){
           try {
               Connection conn=getDatabaseConn();
               String sql="select distinct changed_date from actions_original where changed_date like '"+date+"%'";
               Statement  stmt=conn.createStatement();
               ResultSet rs=stmt.executeQuery(sql);
               while (rs.next()){
                   String changed_date=rs.getString(1);
                   changed_dateList.add(changed_date);
                   String sqlA="select type from actions_original where changed_date='"+changed_date+"'";
                   Statement stmtA=conn.createStatement();
                   ResultSet rsA=stmtA.executeQuery(sqlA);
                   int Acounts=0;int Mcounts=0;int Dcounts=0;
                   while(rsA.next()){
                       String AMDType=rsA.getString(1);
                       if (AMDType.equals("A")){
                           Acounts++;
                       }
                       else if (AMDType.equals("M")){
                           Mcounts++;
                       }
                       else if(AMDType.equals("D")){
                           Dcounts++;
                       }
                   }
                //   System.out.println("A:"+Acounts+"M:"+Mcounts+"D:"+Dcounts);
                   AcountsList.add(Acounts);McountsList.add(Mcounts);DcountsList.add(Dcounts);
                   rsA.close();stmtA.close();;
               }
               rs.close();stmt.close();

               String sqlB="select file_name from actions_original where changed_date like '"+date+"%'";
               Statement stmtB=conn.createStatement();
               ResultSet rsB=stmtB.executeQuery(sqlB);
               while(rsB.next()){
                   String fileName=rsB.getString(1);
                   String fileType=fileName.substring(fileName.lastIndexOf(".")+1);
                   if(fileType.equals("java")){javaCounts++;}
                   else if(fileType.equals("jsp")){jspCounts++;}
                   else if (fileType.equals("html")){htmlCounts++;}
                   else if(fileType.equals("xml")){xmlCounts++;}
                   else{other++;}
               }
               rsB.close();stmtB.close();
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
       }else{
            try {
                Connection conn=getDatabaseConn();
                String sql="select distinct changed_date from actions_original where changed_date like '"+date+"%' and committer= '"+user+"'";
                Statement  stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery(sql);
                while (rs.next()){
                    String changed_date=rs.getString(1);
                    changed_dateList.add(changed_date);
                    String sqlA="select type from actions_original where changed_date='"+changed_date+"'";
                    Statement stmtA=conn.createStatement();
                    ResultSet rsA=stmtA.executeQuery(sqlA);
                    int Acounts=0;int Mcounts=0;int Dcounts=0;
                    while(rsA.next()){
                        String AMDType=rsA.getString(1);
                        if (AMDType.equals("A")){
                            Acounts++;
                        }
                        else if (AMDType.equals("M")){
                            Mcounts++;
                        }
                        else if(AMDType.equals("D")){
                            Dcounts++;
                        }
                    }
                 //   System.out.println("A:"+Acounts+"M:"+Mcounts+"D:"+Dcounts);
                    AcountsList.add(Acounts);McountsList.add(Mcounts);DcountsList.add(Dcounts);
                    rsA.close();stmtA.close();;
                }

                rs.close();stmt.close();

                String sqlB="select file_name from actions_original where changed_date like '"+date+"%'and committer='"+user+"'";
                Statement stmtB=conn.createStatement();
                ResultSet rsB=stmtB.executeQuery(sqlB);
                while(rsB.next()){
                    String fileName=rsB.getString(1);
                    String fileType=fileName.substring(fileName.lastIndexOf(".")+1);
                    if(fileType.equals("java")){javaCounts++;}
                    else if(fileType.equals("jsp")){jspCounts++;}
                    else if (fileType.equals("html")){htmlCounts++;}
                    else if(fileType.equals("xml")){xmlCounts++;}
                    else{other++;}
                }
                rsB.close();stmtB.close();
                //获取影响的代码行数  select revision.commit_date,addLine,reduceLine from hanks,revision where revision.commit_date like '17%' and revision.revision=hanks.rM;
                String sql02="select revision,commit_date from revision where commit_date like '"+date+"%' and  user_name='"+user+"' order by revision";
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
        }

        out.println("{\"changed_dateList\":"+JSONArray.fromObject(changed_dateList).toString()+",\"AcountsList\":"+JSONArray.fromObject(AcountsList).toString()+
                ",\"McountsList\":"+JSONArray.fromObject(McountsList).toString()+",\"DcountsList\":"+JSONArray.fromObject(AcountsList).toString()+",\"typeCounts\":["+javaCounts+","+jspCounts+","+htmlCounts+","+xmlCounts+","+other+"]," +
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
