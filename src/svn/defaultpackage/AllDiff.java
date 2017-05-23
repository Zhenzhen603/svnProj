package svn.defaultpackage;/*此类用来比较所有已提交版本的diff
 * 用于比较版本库中的文件和本地的文件之间的差别
 * 对应Diff，服务器使用SVNKit和VisualSVN时，获取最新的版本号的值不同，详见62、80注释。当前对应使用SVNKit服务器
 *  svnserve -d -r H:\\SVNRepositoriesWeb\GUI_Resp
 * */
//svnserve -d -r H:\\SVNKitRepository3  开启服务器

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class AllDiff {
	static String username="admin";
	static String password="admin";
	static String svnRepositoryURL="svn://localhost/SVNKitRepository3";
	public static void main(String[] args) throws Exception {
				//创建SVNRepository实例并对用户进行验证
				SVNRepository repository = null;
				try { 
					 repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(svnRepositoryURL));
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
			
			//提取所有Diff
			int totalRevisions=logEntries.size();
			for (int i = 0; i <totalRevisions; i++) { // i从0开始试下。这样第一条记录就是0:1 的Diff了 。 VisualSVN服务器下，totalRevisions-1
				showDiff(i,i+1);
				int j=i+1;
					for (SVNLogEntry logEntry : logEntries) 
					{
						//添加注释信息
						if(logEntry.getRevision()==j)
						{
						File file = new File("H:/Diff/diff-r"+i+"："+j+".txt");
						addAnnotations(file,"<--------------------------------->");
						addAnnotations(file,"提交版本"+j+"时的"+"注释信息: ");
						addAnnotations(file,"");
						addAnnotations(file,logEntry.getMessage());
						break;
						}
					}
			}
		System.out.println("-----------------------------------------");
		int totalRevision=totalRevisions;//如果服务器使用VisualSVN，版本号需要-1.使用SVNKit 不需要-1
		System.out.println("提取Diff操作完成,"+"最新版本号为:"+totalRevision);

	}
	//showDiff实现
	public static void showDiff(int revisionA,int revisionB) {
		//声明SVN客户端管理类
		SVNClientManager ourClientManager;
		//初始化支持svn://协议的库。 必须先执行此操作。
		//SVNRepositoryFactoryImpl.setup();
		DAVRepositoryFactory.setup();// https协议支持
		//相关变量赋值  
		//SVNURL配置
			SVNURL repositoryURL = null;
			try {
				repositoryURL = SVNURL.parseURIEncoded(svnRepositoryURL);
			} catch (SVNException e) {
				//
			}
		//String username="sally";
		//String password="sallyssecret";
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		//实例化客户端管理类
		ourClientManager = SVNClientManager.newInstance(
				(DefaultSVNOptions) options, username, password);
		//要比较的文件
	//	File compFile = new File("H:/SVNCheckOutShop3/2.txt");
		//获得SVNDiffClient类的实例。
		SVNDiffClient diff=ourClientManager.getDiffClient();
		//比较结果保存到哪个文件中
		String resultLocation="H:/Diff/diff-r"+ revisionA+"："+revisionB+"."+"txt";
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
			SVNRevision pegRevision=null;//不知道是干什么用的，先弄成空试试 
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

			diff.doDiff(repositoryURL, pegRevision, rN, rM, depth, true, result);
			
			result.close();
			
		} catch (Exception e) {
			System.out.println("获取下面这条"+"Diff出错"+e.toString());
		}
		
		/*
		 * 这是比较版本库中的文件与本地文件的区别的方法
		//比较compFile文件的SVNRevision.WORKING版本和SVNRevision.HEAD版本的差异，结果保存在H:/Diff.txt文件中。
		//SVNRevision.WORKING版本指工作副本中当前内容的版本，SVNRevision.HEAD版本指的是版本库中最新的版本。
		diff.doDiff(compFile, SVNRevision.HEAD, SVNRevision.WORKING, SVNRevision.HEAD, SVNDepth.INFINITY, true, result,null);
		result.close();
		*/
		System.out.println("版本"+revisionA+"和"+"版本"+revisionB+"的比较的结果已经保存在"+resultLocation+"中");
	}	
	
	public static void addAnnotations(File file, String content) {
		BufferedOutputStream bufferedOutputStream = null;
		  try {
		   bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
		   bufferedOutputStream.write("\r\n".getBytes());
		   bufferedOutputStream.write(content.getBytes());
		  } 
		  catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } 
		  catch(IOException e ){
		   e.printStackTrace();
		  }finally{
		   try {
		    bufferedOutputStream.close();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		  }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}