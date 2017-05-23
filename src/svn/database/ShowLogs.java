/* 提取每一次提交版本的时候所影响的文件名 到数据库actions_original表
 * 这是修改后的新showlogs 针对Tomcat库
 * tips:ps.setString(9,entry.getKey().toString().substring(13));//此处添加文件路径，需要酌情对entry.getkey进行修改
 */
package svn.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;
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


public class ShowLogs {
	static long startRevision = 0;
    static long endRevision = -1;//HEAD (the latest) revision
    public static SVNRepository repository=null;
    static int id=0;
	static Connection conn=getDatabaseConn();
    @SuppressWarnings("deprecation")
    public static void showlogs(String svnurl,String username,String password,long start) throws SQLException {
    	System.out.println("生成actions_original...");
    	DAVRepositoryFactory.setup();
    	if (start!=-1) {
    		String idsql="select max(action_id) from actions_original";
    		Statement idstmt=conn.createStatement();
    		ResultSet idrs=idstmt.executeQuery(idsql);
    		while (idrs.next()) {
    			id=idrs.getInt(1);
    		}
    		idrs.close();
    		idstmt.close();
			startRevision=start;
		}

    	if (username==null) {
    		  try {							
    				@SuppressWarnings("deprecation")
    				SVNURL svnUrl=SVNURL.parseURIDecoded(svnurl);
    				repository = DAVRepositoryFactory.create(svnUrl);
    				ISVNAuthenticationManager authManager =SVNWCUtil.createDefaultAuthenticationManager();
    				repository.setAuthenticationManager(authManager);
    			} catch (Exception e) {
    				System.out.println("获取与版本库的连接时：出错-->");
    				e.printStackTrace();
    				System.exit(1);
    			}
		}
    	else {
    		try { 
   			 repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(svnurl));
   			 ISVNAuthenticationManager authManager =SVNWCUtil.createDefaultAuthenticationManager(username, password);
   			 repository.setAuthenticationManager(authManager);
   		} 
   		catch (SVNException e){
   			e.printStackTrace();
   			System.exit(1);
   		}
		}
      
        /*
        try {
			System.out.println( "Repository Root: " + repository.getRepositoryRoot( true ) );
		} catch (SVNException e) {
			System.out.println("respository Root失败");
			e.printStackTrace();
		}
		*/
        try {
            endRevision = repository.getLatestRevision();
        } catch (SVNException svne) {
            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
            System.exit(1);
        }
        System.out.print("开始获取logs...");
        Collection logEntries = null;
        try {
            logEntries = repository.log(new String[] {""}, null,startRevision, endRevision, true, true);

        } catch (SVNException svne) {
            System.out.println("error while collecting log information for '"+ svnurl + "': " + svne.getMessage());
            System.exit(1);
        }
        System.out.print("获取完成，正在遍历...");
        if (startRevision==0) {
        	System.out.println("版本总数："+logEntries.size());
		}else {
			System.out.println("新增版本数："+(logEntries.size()-1));
		}
        String sql="insert into actions_original values (?,?,?,?,?,?,?,?,?)";
        try {
			PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql);
			ps.clearBatch();
			 //对logEntries里面的每一个logEntry进行遍历
			
			for (Iterator entries = logEntries.iterator(); entries.hasNext();) 
			{
			SVNLogEntry logEntry = (SVNLogEntry) entries.next();
			//System.out.print("正在读取："+logEntry.getRevision()+".");
			// 获取修改版本号
			if (startRevision!=0) {
				if (logEntry.getRevision()==startRevision) {
					continue;
				}
			}
			ps.setLong(2, logEntry.getRevision());
			//System.out.println("版本号: " + logEntry.getRevision());
			// 获取提交者
			ps.setString(3, logEntry.getAuthor());
			//System.out.println("提交者: " + logEntry.getAuthor());
			ps.setString(4, null);
			// 获取提交时间
			ps.setString(5, DateFormat.getInstance().format(logEntry.getDate()));
			//System.out.println("日期: " + DateFormat.getInstance().format(logEntry.getDate()));
			// 获取注释信息
			ps.setString(8, logEntry.getMessage());
			//System.out.println("注释信息: " + logEntry.getMessage());
			if (logEntry.getChangedPaths().size()> 0) {
			//System.out.println("受影响的文件、目录:");
			for (Entry<String,SVNLogEntryPath> entry : logEntry.getChangedPaths().entrySet()) 
			{	
				String str=entry.getValue().toString();
				int i=str.lastIndexOf("/")+1;
				id++;
				ps.setInt(1, id);
				ps.setString(6, str.substring(0,1));
				//System.out.println(str.substring(0,1));//获取修改信息的第一个字母作为修改类型
				ps.setString(7, str.substring(i));
				ps.setString(9,entry.getKey().toString().substring(13));//此处添加文件路径，需要酌情对entry.getkey进行修改
				//System.out.println(str.substring(i));//文件名
				/*
				int index=svnurl.indexOf("/");
				index=str.indexOf("/", index+1);
				System.out.println("entry.toString()::"+entry.toString());
				System.out.println("entry.getKey().toString()::"+entry.getKey().toString());
				System.out.println("entry.getKey()::"+entry.getKey());
				if (index>0) {
					ps.setString(9, entry.getKey().toString().substring(index));
				}
				else {
					ps.setString(9, entry.getKey());//版本库的名字
				}
				*/
				//System.out.println(entry.getKey().toString().substring(18)); //显示目录，不显示增删改
				//前18个字符是/SVNKitRepository3
				ps.addBatch();
			}
			ps.executeBatch();
			ps.clearBatch();
			}
			}
			ps.close();
		} 
		catch (SQLException e) {
				System.out.println("64~69 line出错");
				e.printStackTrace();
				System.exit(1);
			}
        setA(start);
        conn.close();
        System.out.println("actions_original完成。");
    }
    //同一文件名 记录全部是M的，修改第一次的M为A
    public static void  setA(long start) throws SQLException {
    	/*:593648)这个文件，填充file_path
    	String sql010="update actions_original set file_path=file_name where file_name='"+"trunk:593648)'";
    	Statement stmt010=conn.createStatement();
    	stmt010.execute(sql010);
    	stmt010.close();
    	*/
    	//修改为A
		String sql01="select distinct file_path from actions_original";
		Statement stmt01=conn.createStatement();
		ResultSet rs01=stmt01.executeQuery(sql01);
		while(rs01.next()){
		 String	file_path=rs01.getString(1);
		 if(file_path.indexOf("'")>-1){continue;};//过滤文件名含有单引号的
		 	String sql02="select min(revision)  from actions_original where file_path='"+file_path+"'";
			Statement stmt02=conn.createStatement();
			ResultSet rs02=stmt02.executeQuery(sql02);
			while(rs02.next()){
				int min_revision=rs02.getInt(1);
				if(min_revision<=start){continue;}
				String sqlset="update actions_original set type='"+"A' where file_path='"+file_path+"' and revision="+min_revision;
				Statement stmt03=conn.createStatement();
				stmt03.execute(sqlset);
				stmt03.close();
			}
			rs02.close();
			stmt02.close();
		}
		rs01.close();
		stmt01.close();
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
