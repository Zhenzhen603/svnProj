/*
 * 提取每一次提交版本的时候所影响的文件名 到数据库actions_original表
 * svnserve -d -r H:\\shop_FSFS_JDK8  开启服务器
 * */
package svn.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysql.jdbc.PreparedStatement;


public class T01 {
	static int id=0;

	public static void main(String[] args) {
		int i=3;
		if (i<0) {
		}
		else if(i==3){
			
		}
		else {
			System.out.println(i);
		}
		System.out.println("done");
	}
		
		
		
		
	

	public static Connection getDatabaseConn(){
		//连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/svnshop";
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
