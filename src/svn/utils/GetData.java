/*
 * 
 * 运行svn服务器：   svnserve -d -r H:\\SVNKitRepository3
 */
package svn.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import svn.jdk8.DisplayFile;
import svn.jdk8.FileDiff;
import svn.jdk8.Filelist;
import svn.database.ShowLogs;

public class GetData {
	//static String svnurl = "http://svn.apache.org/repos/asf/tomcat/trunk/";
	//如果需要管理任意一个版本库，需要设置一个变量 用于指定版本库根的名字，比如asf。然后写程序实现/tomcat/trunk这一段的长度，然后在showlogs里面截取路径的时候使用。
	static String svnurl = null;
	static String username = null;
	static String password = null;
	static Connection conn=getDatabaseConn();
	public static void updateData(String  resp) {
		try {
			svnurl=resp;
			getData();
		} catch (Exception e) {e.printStackTrace();}
	}

	public static void getData() throws Exception{
		
		long cRevAct=currentRev_actionsori();
		// actions_original
    	ShowLogs.showlogs(svnurl, username, password,cRevAct);
	 															//Filelist.filelist(svnurl, username, password,0);//filelogs_original //System.out.println("生成filelogs_original...");
		long cRevCont=currentRev_contentori();//content_original查询最新版本号
		// content_original
 	 	DisplayFile.displayfile(svnurl, username, password,cRevCont);
		// 对数据库进行操作，生成各种需要的表
 	 	updateDatabase(cRevAct,cRevCont);
 		int max=maxDiffid();//数据库中存储的最大diff_id
		// diff_original
  		createDiff(cRevAct,cRevCont,max);
		// hanks
  		CreateHanks.create(max);
		// 删除数据库中的冗余项
		//deleteSurplus();
		conn.close();
		System.out.println("All Done!");
	}
	//对数据库进行操作的方法
	public static void updateDatabase(long cRevAct,long cRevCont) throws SQLException{
		Statement stmt01=conn.createStatement();
		stmt01.clearBatch();
		System.out.println("正在操作数据库...");
		/*
		 * 生成user表,完成增量改进.删掉原来的，重新生成一个新的user表。
		 */
		String sql010="delete from user";
		String sql011="alter table user AUTO_INCREMENT=1";
		String sql01="insert into user (user_name) (select distinct committer from actions_original order by action_id)";
		/*
		 * 生成revision表,完成增量改进
		 */
		String sql02="insert into revision (revision,user_name,commit_date,message) (select distinct revision,committer,changed_date,message from actions_original where revision>"+cRevAct+")";
		String sql03="update revision ,user set revision.user_id=user.user_id where revision.user_name=user.user_name and  revision.revision>"+cRevAct;
		/*
		 * 生成file表,完成增量改进
		 */
		String sql040="alter table file AUTO_INCREMENT=1";//设置file表的file_id的自增为1
		String sql04="insert into file(file_path,file_date)(select distinct file_path,commit_date from content_original where file_addRevision >="+cRevCont+")";
		String sql05="update file ,actions_original set file.file_name=actions_original.file_name where file.file_path=actions_original.file_path and file.file_date=actions_original.changed_date and actions_original.revision>"+cRevAct;
		String sql06="update file ,content_original set file.file_addRevision=content_original.file_addRevision ,file.isTextType=content_original.isTextType where file.file_path=content_original.file_path and file.file_date=content_original.commit_date and content_original.file_addRevision >="+cRevCont;
		stmt01.addBatch(sql010);stmt01.addBatch(sql011);stmt01.addBatch(sql01);stmt01.addBatch(sql02);stmt01.addBatch(sql03);stmt01.addBatch(sql040);stmt01.addBatch(sql04);stmt01.addBatch(sql05);stmt01.addBatch(sql06);
		stmt01.executeBatch();
		stmt01.clearBatch();
		/*
		 * 生成filelogs表
		String sql07="insert into filelogs(id,file_name,file_path,file_author,file_changedDate,file_revision,file_in)(select id,file_name,file_path,file_author,file_changedDate,file_revision,file_in from filelogs_original)";
		String sql08="update filelogs,content_original set filelogs.commit_date=content_original.commit_date where filelogs.file_path =content_original.file_path and filelogs.file_revision=content_original.file_addRevision";
		stmt01.addBatch(sql07);stmt01.addBatch(sql08);stmt01.executeBatch();stmt01.clearBatch();
		System.out.println("正在添加commit_date信息...");
		Add_commit_date_filelogs.add();
		String sql09="update filelogs,file set filelogs.file_id=file.file_id where filelogs.file_path=file.file_path and filelogs.commit_date =file.file_date";
		String sql10="update filelogs,user set filelogs.user_id=user.user_id where filelogs.file_author=user.user_name";
		// 给filelogs_original表添加commit_date 信息，FileDiff.filediff方法要用
		String sql19="update filelogs,filelogs_original set filelogs_original.commit_date=filelogs.commit_date where filelogs.id=filelogs_original.id";
		*/
		
		/*
		 * 生成content表，完成增量改进
		 */
		String sql110="alter table content AUTO_INCREMENT=1";
		String sql111="delete from content where file_addRevision="+cRevCont;
		String sql11="insert into content(content_id,file_path,commit_date,file_addRevision,isTextType,file_content)(select content_id,file_path,commit_date,file_addRevision,isTextType,file_content from content_original where file_addRevision>="+cRevCont+")";
		String sql12="update content,file set content.file_id=file.file_id where content.file_path=file.file_path and content.commit_date=file.file_date and content.file_addRevision>="+cRevCont;
		/*
		 * 生成actions表 ，完成增量改进
		 */
		String sql13="insert into actions(action_id,revision,committer,changed_date,type,file_name,file_path,message) (select action_id,revision,committer,changed_date,type,file_name,file_path,message from actions_original where revision>"+cRevAct+")";
		String sql14="update actions ,content_original set actions.commit_date=content_original.commit_date where actions.file_path=content_original.file_path and actions.revision=content_original.file_addRevision and content_original.file_addRevision >="+cRevCont;
		stmt01.addBatch(sql110);stmt01.addBatch(sql111);stmt01.addBatch(sql11);stmt01.addBatch(sql12);stmt01.addBatch(sql13);stmt01.addBatch(sql14);stmt01.executeBatch();stmt01.clearBatch();
		Add_commit_date_actions.add(cRevAct,cRevCont);//cRevAct,cRevCont没有用到
		String sql141="update actions_original,actions set actions_original.commit_date=actions.commit_date where actions_original.action_id=actions.action_id and actions_original.revision>"+cRevAct;//从actions表获取commit_date到actions_original
		String sql15="update actions ,file set actions.file_id=file.file_id where actions.file_path=file.file_path and actions.commit_date=file.file_date and actions.revision>"+cRevAct;
		String sql16="update actions ,user set actions.user_id=user.user_id where actions.committer=user.user_name and actions.revision>"+cRevAct;
		stmt01.addBatch(sql141);stmt01.addBatch(sql15);stmt01.addBatch(sql16);stmt01.executeBatch();stmt01.clearBatch();
		
		stmt01.close();
	}
	//生成diff_original表的方法
	public static void createDiff(long cRevAct,long cRevCont,int max) throws SQLException {
		System.out.println("生成diff_original...");
		FileDiff.filediff(svnurl, username, password,cRevAct,cRevCont);//diff_original表,cRevAct没有用到
		Statement stmt0A=conn.createStatement();
		stmt0A.clearBatch();
		String sql171="update diff_original,actions_original set diff_original.rMtype=actions_original.type where diff_original.file_path=actions_original.file_path and diff_original.rM=actions_original.revision and diff_original.diff_id >"+max;
		stmt0A.addBatch(sql171);stmt0A.executeBatch();stmt0A.clearBatch();
		String sql172="update diff_original,actions_original set diff_original.rNtype=actions_original.type where diff_original.file_path=actions_original.file_path and diff_original.rN=actions_original.revision and diff_original.diff_id >"+max;
		stmt0A.addBatch(sql172);stmt0A.executeBatch();stmt0A.clearBatch();
		String sql17="insert into diff(diff_id,file_id,file_path,commit_date,rN,rNtype,rM,rMtype,diff)(select diff_id,file_id,file_path,commit_date,rN,rNtype,rM,rMtype,diff from diff_original where diff_id >"+max+" )";
		String sql18="update file,diff set diff.file_id=file.file_id where diff.file_path=file.file_path and diff.commit_date=file.file_date and diff.diff_id>"+max;
		stmt0A.addBatch(sql17);stmt0A.addBatch(sql18);stmt0A.executeBatch();stmt0A.clearBatch();
		stmt0A.close();
		
	}
	//删除冗余项的方法
	public static void deleteSurplus() throws SQLException {
		Statement stmt02=conn.createStatement();
		stmt02.clearBatch();
		//删除冗余表和冗余项
		String sql19="drop table actions_original,content_original,diff_original,filelogs_original";
		String sql20="alter table revision drop column user_name";
		String sql21="alter table filelogs drop column file_name,drop column file_path,drop column file_author,drop column commit_date";
		String sql22="alter table content drop column file_path,drop column commit_date,drop column file_addRevision,drop column isTextType";
		String sql23="alter table actions drop column file_name,drop column file_path,drop column committer,drop column commit_date";
		String sql24="alter table diff drop column file_path,drop column commit_date";
		stmt02.addBatch(sql19);stmt02.addBatch(sql20);stmt02.addBatch(sql21);stmt02.addBatch(sql22);stmt02.addBatch(sql23);stmt02.addBatch(sql24);stmt02.executeBatch();
		stmt02.close();
	}
	//查询数据库中存储的最大diff_id
	public static int maxDiffid() throws SQLException {
		String sql="select max(diff_id) from diff_original";
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		int max=-1;
		while (rs.next()) {
			max=rs.getInt(1);
		}
		rs.close();
		stmt.close();
		return max;
	
	}
	//从actions_original查询当前数据库中存储信息的最新版本号,用于增量添加版本信息
	public  static long currentRev_actionsori() throws SQLException {
		String sql="select max(revision) from actions_original";
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		long start=-1;
		while (rs.next()) {
			start=rs.getInt(1);
		}
		rs.close();
		stmt.close();
		return start;
	}
	//从content_original查询最新版本号
	public  static long currentRev_contentori() throws SQLException {
		String sql="select max(file_addRevision) from content_original";
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		long start=-1;
		while (rs.next()) {
			start=rs.getInt(1);
		}
		rs.close();
		stmt.close();
		return start;
	}
	//获取数据库连接
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
}
