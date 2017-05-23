package svn.backup;
/*
 * 此类用来比较某个文件两个版本的差异
 * 用于比较版本库中的文件和本地的文件之间的差别
 * 对应Diff 
 * 未完成功能
 * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class DoDiff {

	public static void main(String[] args) throws Exception {
		//提取Diff
		showDiff(9,10);
	}
	
	//showDiff实现
	public static void showDiff(int revisionA,int revisionB) {
		//声明SVN客户端管理类
		SVNClientManager ourClientManager;
		//初始化支持svn://协议的库。 必须先执行此操作。
		SVNRepositoryFactoryImpl.setup();
		//相关变量赋值
		//SVNURL配置
			SVNURL repositoryURL = null;
			try {
				repositoryURL = SVNURL.parseURIEncoded("svn://localhost/SVNKitRepository3");
			} catch (SVNException e) {
				e.printStackTrace();
			}
		
		String username="admin";
		String password="admin";
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		//实例化客户端管理类
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, username, password);
		//要比较的文件
		File compFile = new File("/2.txt");
		//获得SVNDiffClient类的实例。
		SVNDiffClient diff=ourClientManager.getDiffClient();
		//比较结果保存到哪个文件中
		String resultLocation="H:/Diff-File.txt";
		//保存比较结果的输出流
		FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(resultLocation);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BufferedOutputStream result =new BufferedOutputStream(fileOutputStream);
			
		
		//比较两个版本之间的差别
		try {
			SVNRevision pegRevision=SVNRevision.HEAD;//不知道是干什么用的，先弄成空试试 
										//pegRevision - a revision in which url is first looked up
			//要比较的两个版本
			SVNRevision rN=null;
			SVNRevision rM=null;
			rN=SVNRevision.create(revisionA);
			rM=SVNRevision.create(revisionB);
			//全递归
			SVNDepth depth=SVNDepth.INFINITY;
			//倒数第二个变量useAncestry
			// if true then the paths ancestry will be noticed while calculating differences, otherwise not
			
			diff.doDiff(compFile, SVNRevision.HEAD, rN, rM, depth, true, result,null);
			result.close();
			
			
			result.close();
			System.out.println("指定文件的Diff结果已经保存在"+resultLocation+"中");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Diff出错");
		}
		
		/*
		 * 这是比较版本库中的文件与本地文件的区别的方法
		//比较compFile文件的SVNRevision.WORKING版本和SVNRevision.HEAD版本的差异，结果保存在H:/Diff.txt文件中。
		//SVNRevision.WORKING版本指工作副本中当前内容的版本，SVNRevision.HEAD版本指的是版本库中最新的版本。
		diff.doDiff(compFile, SVNRevision.HEAD, SVNRevision.WORKING, SVNRevision.HEAD, SVNDepth.INFINITY, true, result,null);
		result.close();
		*/
		
	}	
}