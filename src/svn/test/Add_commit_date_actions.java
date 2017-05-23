/*
 * 用于在actions表中添加commit_date信息，用以区分不同版本下的同名文件。（如1.java 在1~10版本中存在，后来被删除。但是在50版本中又建立了一个重名文件）
 * 
 * 先提取原始的commit_date数据  sql=update actions ,content_original set actions.commit_date=content_original.commit_date where actions.file_path=content_original.file_path and actions.revision=content_original.file_addRevision;
 */
package svn.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class Add_commit_date_actions {
	static Connection conn=getDatabaseConn();
	static ArrayList<Integer> list01=new ArrayList<Integer>(); //有commit_id 记录的id
	static ArrayList<Integer> list02=new ArrayList<Integer>(); //无commit_id 记录的id
	public static void main(String[] args) {
		try {
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn.close();
			System.out.println("actions表添加文件commit_date完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void run() throws SQLException{
		Statement stmt01=conn.createStatement();
		Statement stmt02=conn.createStatement();
		Statement stmt04=conn.createStatement();
		Statement stmt05=conn.createStatement();
		String sql01="select  distinct file_path from content_original";
		ResultSet rs01=stmt01.executeQuery(sql01);
		int j=0;
		while (rs01.next()) {
			j++;

			//比如/新建文本文档.txt 对应两个记录，这里一次只进来一个
			String file_path=rs01.getString(1);
			//System.out.println("正在对第"+j+"个文件："+file_path+"进行处理");
			
			Integer list01id=null;
			String sql02="select action_id,file_path,commit_date from actions where file_path='"+file_path+"' order by action_id desc";//按照action_id的降续排列结果集，方便后续填充值
			ResultSet rs02=stmt02.executeQuery(sql02);
			//构建list01和list02
			while (rs02.next()) {
				list01id=rs02.getInt(1);
				String file_path02=rs02.getString(2);
				String commit_date02=rs02.getString(3);
				if (commit_date02!=null) {
						list01.add(list01id);	
				}
				else {
						list02.add(list01id);
				}
				
			}
		
			//通过判断list02里面的id的值，对空的commi_date赋值。
			int list01size=list01.size();
			int list02size=list02.size();
			//System.out.println("有commit_date值的记录数为"+list01size+",内容为"+list01.toString());
			//System.out.println("无commit_date值的记录数为"+list02size+",内容为"+list02.toString());
			for (int i = 0; i < list01size; i++) {
				int id04 =list01.get(i);
				for (Integer list02id : list02) {
					if (list02id>id04) {
						String list01_commit_date=null;
						String sql04="select commit_date from actions where action_id="+id04;
						ResultSet rs04= stmt04.executeQuery(sql04);
						while (rs04.next()) {
							list01_commit_date=rs04.getString(1);
						}
						
						String sql05="update actions set commit_date='"+list01_commit_date+"' where action_id ="+list02id.intValue()+" and commit_date is null";
						stmt05.execute(sql05);
						
					}
				}
			}
			list01.removeAll(list01);
			list02.removeAll(list02);
			}

		stmt01.close();
		stmt02.close();
		stmt04.close();
		stmt05.close();
		
	}
	public static Connection getDatabaseConn(){
		//连接数据库
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
