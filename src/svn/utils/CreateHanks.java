/*
 * 创建hanks表，需要依赖diff_original表
 */
package svn.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;


public class CreateHanks {
	static int hank_id=0;//hank_id计数器

	public static void create(int max){
		try {
			hanks(max);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//生成hanks表的方法
	public static void hanks(int max) throws SQLException{
		System.out.println("正在更新hanks表...");
		Connection conn=getDatabaseConn();
		//对hank_id 进行赋值
		if (max>0) {
			Statement stmtA=conn.createStatement();
			String sqlA="select max(hank_id) from hanks";
			ResultSet rsA=stmtA.executeQuery(sqlA);
			while (rsA.next()) {
				hank_id=rsA.getInt(1);
			}
			rsA.close();
			stmtA.close();
		}
		
		Statement stmt01=conn.createStatement();
		String sql02="insert into hanks values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql02);
		ps.clearBatch();
		String sql01="select * from diff_original where diff_id>"+max;
		ResultSet rs01=stmt01.executeQuery(sql01);
		while (rs01.next()) {
			//System.out.println(rs01.getInt(1)+rs01.getInt(2)+rs01.getString(3)+rs01.getString(4)+rs01.getInt(5)+rs01.getInt(6)+rs01.getString(7));
			String diff=rs01.getString(9);
			String[] c=diff.split("@@");
			for (int i = 1; i < c.length; i=i+2) {
				String overview=null;
				String affectedLine=null;
				String changed=null;
				//防止有不规矩的的记录 导致角标越界
				try {
					overview=c[0];
					affectedLine=c[i];
					changed=c[i+1];
				} catch (Exception e) {
					continue;
				}
				
				hank_id=hank_id+1;
				ps.setInt(1, hank_id);
				ps.setInt(2, rs01.getInt(1));
				ps.setInt(3, 0);
				ps.setString(4, rs01.getString(3));
				ps.setString(5, rs01.getString(4));
				ps.setInt(6, rs01.getInt(5));
				ps.setString(7, rs01.getString(6));
				ps.setInt(8, rs01.getInt(7));
				ps.setString(9, rs01.getString(8));
				ps.setString(10, overview);
				ps.setString(11, affectedLine);
				ps.setString(12, changed.substring(4));//substring(4)去掉每一个片段前面的"\r\n"换行符
				ps.setString(13, null);//对该hank的分析结果
				ps.addBatch();
			}
			ps.executeBatch();
			ps.clearBatch();
		}
		ps.close();
		rs01.close();
		stmt01.close();
		conn.close();
	}
	//获取数据库连接
	public static Connection getDatabaseConn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/svnshop?useSSL=false";
			String DB_user="root";
			String DB_password="root";
			Connection conn=DriverManager.getConnection(url, DB_user, DB_password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
