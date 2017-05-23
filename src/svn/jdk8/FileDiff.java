package svn.jdk8;
/*
 获取所有文件的所有diff到数据库diff_original，依赖数据库content_original
 bug: diff 方法 多次尝试后，内存溢出异常 ,可以把查询文件名 单独做成一个方法，然后定义指针指向前面失败的那条记录，从那条记录开始获取文件diff
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysql.jdbc.PreparedStatement;

public class FileDiff {
	public static String filePath=null;
	public static Connection conn=getDatabaseConn();
	public static int i=0;//diff_id 的计数器
	public static int j=0;//文件编号的计数器
	public static int k=0;//本地文件缓存的diff文件编号
	public static void filediff(String svnurl,String username,String password,long cRevAct,long cRevCont) throws SQLException {
		if (cRevCont!=-1) {
			System.out.println("增量添加diff...");
			Statement stmtA=conn.createStatement();
			String sqlA="select max(diff_id) from diff_original";
			ResultSet rsA=stmtA.executeQuery(sqlA);
			while (rsA.next()) {
				i=rsA.getInt(1);
				k=i;
			}
			rsA.close();
			stmtA.close();
		}
		Statement stmt01=conn.createStatement();
		String sql01="select file_path,commit_date from content_original where isTextType=1";
		ResultSet rs01=stmt01.executeQuery(sql01);
		rs01.last(); //结果集指针到最后一行数据  
		int n = rs01.getRow();  
		System.out.println("总文件数："+n);  
		rs01.beforeFirst();//指针回到最初位置
		while (rs01.next()) {
			String filePathString=rs01.getString(1);// 查询到的原始filepath
			String commit_date=rs01.getString(2);
			String filePath=svnurl+filePathString;//包装成可以调用filediff方法的filePath
			//过滤文件名非法的，比如带有单引号的'
			if(filePath.indexOf("'")>-1){
				if (cRevCont==-1) {
					i=i+1;
					String sql03A="insert into diff_original values (?,?,?,?,?,?,?,?,?)";
					PreparedStatement psA=(PreparedStatement) conn.prepareStatement(sql03A);
					psA.clearBatch();
					psA.setInt(1, i);
					psA.setInt(2, 0);
		        	psA.setString(3, filePathString);
		        	psA.setString(4, commit_date);
					psA.setLong(5, -1);
					psA.setString(6, null);
					psA.setLong(7, -1);
					psA.setString(8, null);
					psA.setString(9, "文件名非法");		
					psA.addBatch();
					psA.executeBatch();
			        psA.close();
			        continue;
				}
				continue;
			}
			//根据传进来的唯一文件路径和提交时间，查询当前数据库中存储的最新版本号
			int maxrM=-1;
			Statement stmtB=conn.createStatement();
			String sqlB="select max(rM) from diff_original where file_path='"+filePathString+"' and commit_date='"+commit_date+"'";
			ResultSet rsB=stmtB.executeQuery(sqlB);
			while (rsB.next()) {
				maxrM=rsB.getInt(1);
			}
			rsB.close();
			stmtB.close();
			
			Statement stmt02=conn.createStatement();
			String sql02="select revision from actions_original where file_path='"+filePathString+"' and commit_date='"+commit_date+"'";
			ResultSet rs02=stmt02.executeQuery(sql02);
			ArrayList<Integer> fileInList=new ArrayList<Integer>();
			while (rs02.next()) {
				int finrevision=rs02.getInt(1);
				if (finrevision<maxrM) {
					continue;
				}
				fileInList.add(finrevision);
			}
			rs02.close();
			stmt02.close();
			for (int index=0;index<fileInList.size()-1;) {
				long rN=(long)fileInList.get(index);
				index=index+1;
				long rM=(long)fileInList.get(index);
				run(filePath,filePathString,commit_date, rN, rM,svnurl,username,password);
			}
			j=j+1;
			if (fileInList.size()>1){
				System.out.println(j+",文件名："+filePathString+"  diff完成");
			}

		}
		rs01.close();
	    stmt01.close();
		conn.close();
		System.out.println("全部diff操作完成");
	}

	public static void run(String filePath,String filePathString,String commit_date,long rN,long rM,String svnurl,String username,String password){
		
		//初始化支持http://协议的库。 必须先执行此操作。
		DAVRepositoryFactory.setup();
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(svnurl);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		//实例化客户端管理类
		SVNClientManager ourClientManager=null;
		if(username==null){
			ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options);
		}
		else {
			ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, username, password);
		}
		//要比较的文件
		//	File compFile = new File(filePath);
		//获得SVNDiffClient类的实例。
		SVNDiffClient diff=ourClientManager.getDiffClient();
		
		try {
			k=k+1;
			String str="D:diff"+k+".txt";
			BufferedOutputStream result =new BufferedOutputStream(new FileOutputStream(str));
			//比较compFile文件的SVNRevision.WORKING版本和	SVNRevision.HEAD版本的差异，结果保存在E:/result.txt文件中。
			//SVNRevision.WORKING版本指工作副本中当前内容的版本，SVNRevision.HEAD版本指的是版本库中最新的版本。
			SVNRevision rNSVNRevision=SVNRevision.create(rN);
			SVNRevision rMSVNRevision=SVNRevision.create(rM);
			SVNURL url = SVNURL.parseURIEncoded(filePath);
			//Peg Revision表示在这个版本下定位那个元素，这一定是唯一的
			diff.doDiff(url, rNSVNRevision, rNSVNRevision, rMSVNRevision, SVNDepth.INFINITY, true, result);
			result.close();
			//根据传进来的参数生成的diff
			String str02=null;
			String data=null;
			BufferedReader br=new BufferedReader(new FileReader(new File(str)));
			while ((data =br.readLine())!=null) {
				str02=str02+data+"\r\n";
			}
			if (str02!=null) {
				i=i+1;
				String sql03="insert into diff_original values (?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql03);
				ps.clearBatch();
				ps.setInt(1, i);
				ps.setInt(2, 0);
	        	ps.setString(3, filePathString);
	        	ps.setString(4, commit_date);
				ps.setLong(5, rN);
				ps.setString(6, null);
				ps.setLong(7, rM);
				ps.setString(8, null);
				ps.setString(9, str02);		
				ps.addBatch();
				ps.executeBatch();
		        ps.close();
				br.close();
			}

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("diff方法出错，正在重新获取："+filePathString+"-"+rN+":"+rM);
			try {
    			Thread.sleep(5000);
    		} catch (Exception se) {
    			System.out.println("线程暂停5秒错误");
    			se.printStackTrace();
    		}
			run(filePath,filePathString,commit_date, rN, rM,svnurl,username,password);
			
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
			System.out.println("连接数据库失败");
			return null;
		}
		
				
	}
	
}