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
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by ZhenZhen on 2017/6/11.
 */
@WebServlet(name = "CheckRevision")
public class CheckRevision extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String filePath=request.getParameter("filePath");
        ArrayList<Integer> rNs=new ArrayList<Integer>();
        ArrayList<Integer> rMs=new ArrayList<Integer>();
        try {
            Connection conn=QueryAMDServlet.getDatabaseConn();
            String sql01="select rN,rM from diff where file_path='"+filePath+"'";
            Statement stmt01=conn.createStatement();
            ResultSet rs01=stmt01.executeQuery(sql01);
            while(rs01.next()){
                int rN=rs01.getInt(1);
                int rM=rs01.getInt(2);
                rNs.add(rN);
                rMs.add(rM);
            }
            rs01.close();stmt01.close();conn.close();
        }catch (Exception e){e.printStackTrace();}

        out.println("{\"rNs\":"+ JSONArray.fromObject(rNs).toString()+",\"rMs\":"+JSONArray.fromObject(rMs).toString()+"}");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
