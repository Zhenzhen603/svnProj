package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ZhenZhen on 2017/6/7.
 */
@WebServlet(name = "FileServlet")
public class FileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/text;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String filePath=request.getParameter("filePath");
        String overView=null;
        try {
            overView=queryOverview(filePath);
        } catch (Exception e) {e.printStackTrace();}

        out.println(overView);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    public  static String queryOverview(String filePath) throws Exception{
        Connection conn=QueryAMDServlet.getDatabaseConn();
        String sql01="select * from content where file_path='"+filePath+"'";
        Statement stmt01 =conn.createStatement();
        ResultSet rs01=stmt01.executeQuery(sql01);
        String commit_date=null;int addRevision=-1;int isTextType=-1;String content=null;
        while (rs01.next()){
            commit_date=rs01.getString(4);
            addRevision=rs01.getInt(5);
            isTextType=rs01.getInt(6);
            content=rs01.getString(7);
        }
     //  String contentRe=content.replaceAll("\n","<br>");

     //   System.out.println(("\"commit_date\":\""+commit_date+"\",\"addRevision\":"+addRevision+",\"isTextType\":"+isTextType+",\"content\":\""+contentRe+"\"").substring(395));
        if(isTextType==-1){
            return "-1";
        }else{
            return "commit_date:"+commit_date+"\r\n"+"addRevision:"+addRevision+"\r\n"+"isTextType:"+isTextType+"\r\n"+"content:\r\n"+content;
        }

    }
}

