/*
 * 提取所有版本中的文件列表到数据库filelogs_original表
 * 
 * Tips:http协议的和SVN协议的，这一项是不一样的  totalRevisions=logEntries.size();  可以酌情调节，size不一定是总共的版本数 可以+1或-1
 */
package svn.backup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysql.jdbc.PreparedStatement;

public class Filelist {
   static int totalRevisions;
   static long revision=1;   //指定版本库的版本（初始版本，即从哪个版本开始遍历）
   static String path="";	//指定要获取的版本库文件的目录，如"src/shop" 代表只遍历shop文件夹下的内容
   static int id=0;
   static String username=null;
   static String password=null;
   static String svnurl=null;
   static Connection conn=Filelist.getDatabaseConn();//获取数据库连接对象 conn
   static SVNRepository repository = null;
   /**
    *@param revisionStart 从哪个版本开始
    */
	public static void filelist(String svnurl2,String username2,String password2,long revisionStart) throws SQLException {
		username=username2;
		password=password2;
		svnurl=svnurl2;
		//删除之前插入的最后一个版本号的不完整数据
		String delsql="delete  from filelogs_original where file_in="+revisionStart;
		Statement stmt01=conn.createStatement();
		stmt01.execute(delsql);
		stmt01.close();
		Statement stmt02=conn.createStatement();
		String queryid="select max(id) from filelogs_original";
		ResultSet rs= stmt02.executeQuery(queryid);
		while(rs.next()){
			id=rs.getInt(1);	
		}
		rs.close();
		stmt02.close();
		System.out.println("本次从版本："+revisionStart+" 开始>>");
		//初始化版本库
		DAVRepositoryFactory.setup();
		Filelist filelist=new Filelist();
		//获取总共的版本数量
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
		System.out.println("用户认证完成");
		Collection logEntries = null;
		try {
			logEntries = repository.log(new String[] {""}, null,0, -1, true, true);
			totalRevisions=logEntries.size();
	        System.out.println("Repository Root: " + repository.getRepositoryRoot(true));
	        System.out.println("Repository UUID: " + repository.getRepositoryUUID(true));
		} 
		catch (SVNException svne) {
			System.out.println("try出错，获取历史记录出错，版本号："+revisionStart);
			svne.printStackTrace();
			try {
	        	System.out.println("正在重新尝试版本："+revisionStart);
	        	try {
	    			Thread.sleep(5000);
	    		} catch (Exception e) {
	    			System.out.println("线程暂停5秒错误");
	    			e.printStackTrace();
	    		}
				Filelist.filelist(svnurl, username, password, revisionStart);
			} catch (SQLException e) {
				System.out.println("SQL错误");
				e.printStackTrace();
			}
			System.exit(1);
		}
		//获取总共的版本数量
		System.out.println("总版本数："+totalRevisions);//输出总共版本数量，用于遍历所有版本
		/*遍历所有版本的文件个数记录*/
		for (long i = revisionStart; i < totalRevisions+1; i++) {
			revision=i;
			filelist.getMessage(revision, path,svnurl);//连接svn库,输出如下信息---Repository Root: svn://localhostRepository   UUID: 01237ecc-5701-0010-a446-c5b5ddbdca2c  Version:50,Path:src/shop/cart
		}

		//关闭数据库连接
		try {
			conn.close();
			System.out.println("数据提取操作完成！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("关闭数据库连接失败");
		}//关闭数据库连接
		System.out.println();
	}

    public static void listEntries(String path,long revision) {
        Collection entries=null;
		try {
			entries = repository.getDir(path,revision, null,(Collection) null);
		} catch (SVNException e1) {
			e1.printStackTrace();
			try {
	        	System.out.println("正在重新尝试版本："+revision);
	        	try {
	    			Thread.sleep(5000);
	    		} catch (Exception e) {
	    			System.out.println("线程暂停5秒错误");
	    			e.printStackTrace();
	    		}
				Filelist.filelist(svnurl, username, password, revision);
			} catch (SQLException e) {
				System.out.println("SQL错误");
				e.printStackTrace();
			}
			System.exit(1);
		}
        int totalRevisions=entries.size();//总共有多少个版本
        
        
        Iterator iterator = entries.iterator();
		String sql="insert into filelogs_original values (?,?,?,?,?,?,?,?)";
		PreparedStatement ps=null;
		try {
			ps=(PreparedStatement) conn.prepareStatement(sql);
			ps.clearBatch();
			  while (iterator.hasNext()) {
		            SVNDirEntry entry = (SVNDirEntry) iterator.next();
		            id++;
		            ps.setInt(1, id);
					ps.setString(2,entry.getName());//文件名
					ps.setString(3,"/" + (path.equals("") ? "" : path + "/")+ entry.getName());//文件完整路径
					ps.setString(4, entry.getAuthor());//作者
					Date date=entry.getDate();
					DateFormat dateFormat=DateFormat.getInstance();
					String dateString=dateFormat.format(date);
					ps.setString(5, null);//文件的commitdate
					ps.setString(6,dateString); 	// 获取提交时间
					ps.setLong(7, entry.getRevision());//修订版本号
					ps.setLong(8, revision);
					ps.addBatch();
		            if (entry.getKind() == SVNNodeKind.DIR) {
		                listEntries((path.equals("")) ? entry.getName(): path + "/" + entry.getName(),revision);
		            }
		        }
			  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("程序56-60行运行出错");
		}
		
		  try {
			ps.executeBatch();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  
    }
    
	
	public void getMessage(long revision,String path,String svnurl){

	  //  long revision=50;   //指定版本库的版本
	  //  String path="src/shop/cart";		//指定要获取的版本库文件的目录，如"src/shop" 代表只遍历shop文件夹下的内容
	//	System.out.print("版本："+revision+"...");
     //   listEntries(path,revision);
        
    try { 
    	//验证该指定版本下的""目录，是否没有内容或者是一个文件而已。
        SVNNodeKind nodeKind = repository.checkPath(path,revision);
        if (nodeKind == SVNNodeKind.NONE) {
            System.err.println("There is no entry at '" + svnurl + "'.");
            System.exit(1);
        } else if (nodeKind == SVNNodeKind.FILE) {
            System.err.println("The entry at '" + svnurl + "' is a file while a directory was expected.");
            System.exit(1);
        }

        //System.out.println("Repository LatestRevidion: " + repository.getLatestRevision());
        //System.out.println("Version:"+revision+",Path:"+path);
        System.out.print("版本："+revision+"完成.");
        listEntries(path,revision);
    } catch (SVNException svne) {
        System.err.println("error while listing entries: "+ svne.getMessage());
        try {
        	System.out.println("正在重新尝试版本："+revision);
        	try {
    			Thread.sleep(5000);
    		} catch (Exception e) {
    			System.out.println("线程暂停5秒错误");
    			e.printStackTrace();
    		}
			Filelist.filelist(svnurl, username, password, revision);
		} catch (SQLException e) {
			System.out.println("SQL错误");
			e.printStackTrace();
		}
        System.exit(1);
    }
    
    long latestRevision = -1;
    try {
        latestRevision = repository.getLatestRevision();
    } catch (SVNException svne) {
        System.err.println("error while fetching the latest repository revision: "+ svne.getMessage());
        System.exit(1);
    }
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
