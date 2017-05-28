package servlet;

import org.objectweb.asm.tree.TryCatchBlockNode;
import svn.utils.GetData;

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

/**
 * Created by ZhenZhen on 2017/5/28.
 */
@WebServlet(name = "InitStatusText")
public class InitStatusText extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            query(request,response);

        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    public static void query(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String resp=request.getParameter("resp");
        Connection conn=QueryAMDServlet.getDatabaseConn();
        Statement stmt01=conn.createStatement();
        String queryRevision="select max(revision) from revision ";
        ResultSet rs01=stmt01.executeQuery(queryRevision);
        int revision=0;
        while(rs01.next()){
            revision=rs01.getInt(1);
        }
        rs01.close();stmt01.close();
        String sql02="select user_name,commit_date from revision where revision ="+revision;
        Statement stmt02=conn.createStatement();
        ResultSet rs02=stmt02.executeQuery(sql02);
        String username=null;
        String commit_date=null;
        while(rs02.next()){
            username=rs02.getString(1);
            commit_date=rs02.getString(2);
        }
        rs02.close();stmt02.close();conn.close();

        out.println("{\"repository\":\""+resp+"\",\"revision\":"+revision+",\"commit_date\":\""+commit_date+"\",\"username\":\""+username+"\"}");



    }
}
