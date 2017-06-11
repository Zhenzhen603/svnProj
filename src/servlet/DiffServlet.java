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
 * Created by ZhenZhen on 2017/6/11.
 */
@WebServlet(name = "DiffServlet")
public class DiffServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/text;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String filePath=request.getParameter("filePath");
        int rN=Integer.parseInt( request.getParameter("rN"));
        int rM=Integer.parseInt( request.getParameter("rM"));
        try {
            Connection conn=QueryAMDServlet.getDatabaseConn();
            Statement stmt01=conn.createStatement();
            String sql01="select rN,rM,diff from diff where file_path='"+filePath+"'and rN>="+rN+" and rM<="+rM;
            ResultSet rs01=stmt01.executeQuery(sql01);
           // out.println("filePath:"+filePath+"\r\n--------------------------------------------------------------------");
            int i=0;
            while(rs01.next()){
                i++;
                int rN01=rs01.getInt(1);
                int rM01=rs01.getInt(2);
                String diff=rs01.getString(3);
                out.println("rN:"+rN01+",rM:"+rM01+"(diff:"+i+")\r\n---------------------------\r\n"+diff);
            }
            rs01.close();stmt01.close();conn.close();
        }catch (Exception e){e.printStackTrace();}




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
