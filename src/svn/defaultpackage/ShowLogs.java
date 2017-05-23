package svn.defaultpackage;/*
 * 显示指定版本库的所有历史记录
 * svnserve -d -r H:\\shop_FSFS_JDK8  开启服务器
 * */
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class ShowLogs {

	public static void main(String[] args) {
		String username="admin";
		String password="admin";
		//创建SVNRepository实例并对用户进行验证
		DAVRepositoryFactory.setup();
		SVNRepository repository = null;
		try { 
			 repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded("svn://localhost/SVNKitRepository3"));
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
		repository.log(new String[] { "/" }, logEntries, startRevision,endRevision, true, false);
		} 
		catch (SVNException svne) {
		//System.out.println("获取资源库历史记录时出错" + svne.getMessage()); 
		System.out.println("try出错，获取历史记录出错");
		svne.printStackTrace();
		System.exit(1);
		}
		System.out.println(logEntries.size());
		 //对logEntries里面的每一个logEntry进行遍历
		for (SVNLogEntry logEntry : logEntries) 
		{
		System.out.println("---------------------------------------------");
		// 获取修改版本号
		System.out.println("版本号: " + logEntry.getRevision());
		// 获取提交者
		System.out.println("提交者: " + logEntry.getAuthor());
		// 获取提交时间
		System.out.println("日期: " + logEntry.getDate());
		// 获取注释信息
		System.out.println("注释信息: " + logEntry.getMessage());
		if (logEntry.getChangedPaths().size()> 0) {
		System.out.println();
		System.out.println("受影响的文件、目录:");
		for (Entry<String,SVNLogEntryPath> entry : logEntry.getChangedPaths().entrySet()) 
		{
			System.out.println(entry.getValue());
			System.out.println(entry.getValue());
			System.out.println(entry.getKey()); //显示目录，不显示增删改
		}
		}
		 
		}
		
	}

}
