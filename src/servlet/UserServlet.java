package servlet;

import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by ZhenZhen on 2017/6/3.
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String userName=request.getParameter("userName");
        String userDate=request.getParameter("userDate");
        Connection conn=QueryAMDServlet.getDatabaseConn();
        ArrayList<String> names=addNames(conn);//为selectName添加 names





        out.println("{\"names\":"+ JSONArray.fromObject(names).toString()+"}");


        try {conn.close();}catch (Exception e){e.printStackTrace();}//关闭数据库
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request,response);
    }

    public  static ArrayList<String> addNames(Connection conn){
        ArrayList<String> names=new ArrayList<String>();
        try {
            Statement stmt=conn.createStatement();
            String sql="select user_name from user";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                String userName=rs.getString(1);
                names.add(userName);
            }
            rs.close();stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }
}
