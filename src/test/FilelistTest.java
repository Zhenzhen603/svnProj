/*
 * 提取所有版本中的文件列表到数据库filelogs_original表
*/
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysql.jdbc.PreparedStatement;

public class FilelistTest {
	static String path="";
	static int id=0;
	static long revisionStart=0;
    static String username=null;
    static String password=null;
    static String svnurl=null;
	static SVNRepository repository = null;
	static ArrayList<Integer> revisionList=new ArrayList<Integer>();
	static Connection conn=getDatabaseConn();//获取数据库连接对象 conn
	static PreparedStatement ps=null;
	   public static void filelist(String svnurl2,String username2,String password2,long revisionStart) throws SQLException {
		username=username2;
		password=password2;
		svnurl=svnurl2;
        setupLibrary();
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

	
        //根据是否有用户名和密码 进行认证
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
	 			System.out.println("使用密码获取与版本库的连接时：出错-->");
	 			e.printStackTrace();
	 			System.exit(1);
	 		}
		}
																			       /* 
																			        try {
																			            SVNNodeKind nodeKind = repository.checkPath(path, -1);
																			            if (nodeKind == SVNNodeKind.NONE) {
																			                System.err.println("There is no entry at '" + svnurl + "'.");
																			                System.exit(1);
																			            } else if (nodeKind == SVNNodeKind.FILE) {
																			                System.err.println("The entry at '" + svnurl + "' is a file while a directory was expected.");
																			                System.exit(1);
																			            }
																			            System.out.println("Repository Root: " + repository.getRepositoryRoot(true));
																			            System.out.println("Repository UUID: " + repository.getRepositoryUUID(true));
																			
																			            
																			            listEntries(repository, "");
																			        } catch (SVNException svne) {
																			            System.err.println("error while listing entries: "
																			                    + svne.getMessage());
																			            System.exit(1);
																			        }
																			        */
    	//查询所有的版本号 并赋值到revisionList中
    	getRevisionList();
    	int start=0;
    	if(revisionStart!=0){
    		for (int x=0;x<revisionList.size();x++) {
    			int y=revisionList.get(x);
				if(revisionStart==y){
					start=x;
					break;
				}
			}
    	}
		String sql="insert into filelogs_original values (?,?,?,?,?,?,?,?)";
		ps=(PreparedStatement) conn.prepareStatement(sql);
		int k=start+1;
    	//根据版本号遍历路径下的所有文件
    	for (int i = start; i < revisionList.size(); i++) {
    		revisionStart=revisionList.get(i);
    		System.out.println("本次从版本号："+revisionStart+" 开始,这是第"+k+"个版本日志记录->");
    		ps.clearBatch();
    		long timeA=System.currentTimeMillis();
    		listEntries(revisionStart,path);
    		ps.executeBatch();
    		long timeB=(System.currentTimeMillis()-timeA)/1000;
    		System.out.print("版本："+revisionStart+"完成。耗时"+timeB+"s.");
		}
    	ps.close();
    	conn.close();
  }
    //查询所有的版本号 并赋值到revisionList中的实现方法
	private static void getRevisionList() throws SQLException  {
		
		// TODO Auto-generated method stub
		String revsqlString="select distinct(revision) from actions_original";
		Statement stmtrev=conn.createStatement();
		ResultSet rsrev= stmtrev.executeQuery(revsqlString);
		while(rsrev.next()){
			revisionList.add(rsrev.getInt(1));	
		}
		System.out.println("总版本数："+revisionList.size());
		rsrev.close();
		stmtrev.close();
	}
	
	
	//连接数据库
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

    /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
    	
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
       // SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
       // FSRepositoryFactory.setup();
    }

    public static void listEntries(long revision, String path2) throws SQLException {
 
        Collection entries=null;
		try {
			entries = repository.getDir(path2,revision, null,(Collection) null);
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
				filelist(svnurl, username, password, revision);
			} catch (SQLException e) {
				System.out.println("SQL错误");
				e.printStackTrace();
			}
			System.exit(1);
		}
		Iterator iterator = entries.iterator();
		
		  while (iterator.hasNext()) {
	            SVNDirEntry entry = (SVNDirEntry) iterator.next();
	            if (entry.getKind() == SVNNodeKind.DIR) {
	                listEntries(revision,(path.equals("")) ? entry.getName(): path + "/" + entry.getName());
	            }
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
	            
	        }
		  
    }
}