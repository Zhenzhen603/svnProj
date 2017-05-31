package servlet;

import org.apache.struts2.components.ElseIf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by ZhenZhen on 2017/5/23.
 */
@WebServlet(name = "QueryAMDServlet")
public class QueryAMDServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String date=request.getParameter("date").substring(2);
        //链接数据库并查询对应日期的信息
        int Acounts=0;int Mcounts=0;int Dcounts=0;
        int javaCounts=0;int jspCounts=0;int htmlCounts=0;int xmlCounts=0;int other=0;
        try {
            Connection conn=getDatabaseConn();
            String sql="select type,file_name from actions_original where changed_date like '"+date+"%'";
            Statement  stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                String AMDType=rs.getString(1);
                String fileName=rs.getString(2);
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
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("{\"counts\":["+Acounts+","+Mcounts+","+Dcounts+"],\"typeCounts\":["+javaCounts+","+jspCounts+","+htmlCounts+","+xmlCounts+","+other+"]}");
        System.out.println("servlet执行完成----"+"{\"counts\":["+Acounts+","+Mcounts+","+Dcounts+"]}"+"java"+javaCounts+",jsp"+jspCounts+",html"+htmlCounts);
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
