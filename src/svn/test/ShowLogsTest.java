/* 提取每一次提交版本的时候所影响的文件名 到数据库actions_original表
 * 这是修改后的新showlogs 针对Tomcat库
 * tips:ps.setString(9,entry.getKey().toString().substring(13));//此处添加文件路径，需要酌情对entry.getkey进行修改
 */
package svn.test;
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


public class ShowLogsTest {
	static long startRevision = 0;
    static long endRevision = -1;//HEAD (the latest) revision
    public static SVNRepository repository=null;
    static int id=0;
	static String svnurl = "http://svn.apache.org/repos/asf/tomcat/trunk/";
	static String username = null;
	static String password = null;
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws SQLException {
    	//获取数据库连接对象 conn
    	DAVRepositoryFactory.setup();
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
        System.out.println("actions_original完成。");
    }

}
