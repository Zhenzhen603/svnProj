package svn.defaultpackage;
/*
 * 获取修改版本号、获取提交者、获取提交时间、获取注释信息、获取变更文件列表
 * 将获取到的信息导入到数据库
 * */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysql.jdbc.PreparedStatement;

public class Do_DataBase {
	static int id=0;
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String username="admin";
		String password="admin";
		String respUrl="svn://localhost/SVNKitRepository3";
		//创建SVNRepository实例并对用户进行验证
		SVNRepository repository = null;
		try { 
			 repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(respUrl));
			 ISVNAuthenticationManager authManager =SVNWCUtil.createDefaultAuthenticationManager(username, password);
			 repository.setAuthenticationManager(authManager);
		} 
		catch (SVNException e){
			e.printStackTrace();
			System.exit(1);
		}
		 
		//获取资源库的历史记录
		long startRevision = 0;
		long endRevision = -1;//最新修订版本
		Collection<SVNLogEntry> logEntries = new LinkedList<SVNLogEntry>();
		try {
		repository.log(new String[] { "/" }, logEntries, startRevision,endRevision, true, true);
		} 
		catch (SVNException svne) {
		System.out.println("获取资源库历史记录时出错" + svne.getMessage()); 
		System.exit(1);
		}
		int totalRevision=logEntries.size()-1;
		System.out.println("总版本号:"+totalRevision+",正在提取数据到数据库...");
		//连接数据库
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/svnshopcheckout3";
		String DB_user="root";
		String DB_password="root";
		Connection conn=DriverManager.getConnection(url, DB_user, DB_password);
		String sql="insert into logs values (?,?,?,?,?)";
		PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql);
		ps.clearBatch();
		//对logEntries里面的每一个logEntry进行遍历  logEntry：一条日志信息 entry：某一条日志信息里面的其中一个变更记录
		for (SVNLogEntry logEntry : logEntries) 
			{
				
				if (logEntry.getChangedPaths().size()> 0) 
					{
						for (Entry<String,SVNLogEntryPath> entry : logEntry.getChangedPaths().entrySet()) 
							{
								Do_DataBase.id++;
								ps.setInt(1, Do_DataBase.id);
								ps.setLong(2, logEntry.getRevision());// 获取修改版本号
								ps.setString(3, logEntry.getAuthor());// 获取提交者
								Date date=logEntry.getDate();
								DateFormat dateFormat=DateFormat.getInstance();
								String dateString=dateFormat.format(date);
								ps.setString(4,dateString); 	// 获取提交时间
								ps.setString(5, logEntry.getMessage());// 获取注释信息
								//String str01=entry.getValue().toString();//获取变更文件列表
								//ps.setString(6, str01);
								ps.addBatch();
								
								System.out.println("已提取第"+Do_DataBase.id+"条变更记录。");
							}
					}
			}
		System.out.println("正在执行executeBatch()...");
		ps.executeBatch();
		ps.close();
		conn.close();
		System.out.println("Done");
	}

}
