package svn.jdk8;
/* todo actions_original表自动增量添加信息后，content——original表需要同时查询是否有新增的A类型的记录 并获取文件内容
 * 将文件第一次提交的时候的内容，写入数据库
 * 需依赖actions_original表，提取到content_original表中
 * tips:filePath=rs01.getString(3).substring(1);//获取到唯一的文件名path
 * */
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysql.jdbc.PreparedStatement;


public class DisplayFile {
    static SVNRepository repository = null;
    static int content_id=0;
    static int m=0;
    static int t=0;//跳过的文件  计数器
    static int nonExistent=0;// 不存在的文件计数器
    static int isDir=0;//是一个目录的计数器
    static Connection conn=getDatabaseConn();
    public static void displayfile(String svnurl,String username,String password,long start) throws SQLException {
    	System.out.println("生成content_original...");
    	String filePath = null;
    	String changed_date=null;
        long revision=0;
        //如果start>0说明content_original中已经有数据，删除最后一个版本号的不完整数据。并调整content_id
	    if (start>0) {	
		    String delsql="delete  from content_original where file_addRevision="+start;
			Statement stmt02=conn.createStatement();
			stmt02.execute(delsql);
			stmt02.close();
			//为content_id 赋值
			String sql04="select max(content_id) from content_original";
			Statement stmt04=conn.createStatement();
			ResultSet rs04=stmt04.executeQuery(sql04);
			while(rs04.next()){
				content_id=rs04.getInt(1);
			}
			rs04.close();
			stmt04.close();
			System.out.println("增量获取数据，已记录"+content_id+".本次从版本："+start+"开始");
		}
    //初始化连接	
    setupLibrary();																						
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
			System.out.println("获取与版本库的连接时：出错-->");
			e.printStackTrace();
			System.exit(1);
		}
	}
    /*获取最新版本库号*/
    long latestRevision = -1;
    try {
        latestRevision = repository.getLatestRevision();
        System.out.print("最新版本号：" + latestRevision);
        System.out.print(",正在提取数据...");
    } catch (SVNException svne) {
        System.err.println("获取版本库的最新版本号时失败: " + svne.getMessage());
        System.exit(1);
    }
  
   //获取数据库连接对象和Statement对象
    String sql01="select revision,changed_date,file_path from actions_original where type='A' order by revision";
    try {
		Statement stmt01=conn.createStatement();
		ResultSet rs01= stmt01.executeQuery(sql01);
		rs01.last(); //结果集指针到最后一行数据  
		int n = rs01.getRow();  
		System.out.println("总文件数："+n);
		System.out.print("已完成：");
		rs01.beforeFirst();//指针回到最初位置
		while (rs01.next()) {
				revision=rs01.getInt(1);//获取该文件被Add时的版本号
				if (revision<start) {
					t++;
					continue;
				}
				//为了能查到文件，需要把action_original里面的path的第一条斜线拿掉01  234
				String tmpString=rs01.getString(3);
			   	if(tmpString.length()>1){
			   		filePath=tmpString.substring(1);//获取到唯一的文件名path}
			   	}
			   	else if(tmpString.length()<2){continue;}
			   	if (filePath.endsWith(".jar")) {
			   		content_id++;
			   		String sqlA="insert into content_original values (?,?,?,?,?,?)";
			        PreparedStatement psA=(PreparedStatement) conn.prepareStatement(sqlA);
					psA.clearBatch();
		        	psA.setInt(1, content_id);
		        	psA.setString(2, "/"+filePath);
		        	psA.setString(3, changed_date);
					psA.setLong(4, revision);
					psA.setInt(5, 0);
					psA.setString(6, "jar文件");
					psA.addBatch();
					psA.executeBatch();
					psA.close();
					continue;
				}
				changed_date=rs01.getString(2);//获取到唯一的文件名path对应的date
					//判断该文件是否在指定版本中存在，是否为文件目录
					try {
			            SVNNodeKind nodeKind = repository.checkPath(filePath, revision);
			            if (nodeKind == SVNNodeKind.NONE) {
			            	nonExistent++;
			                //System.err.println("文件：" + filePath + "在版本"+revision+"中不存在"+"，已自动跳过");
			                continue;
			            } else if (nodeKind == SVNNodeKind.DIR) {
			            	isDir++;
			                //System.err.println("文件：“" + filePath +"”是一个目录"+"，已自动跳过");
			                continue;
			            }
			        } catch (SVNException svne) {
			           // System.err.println("查询文件与对应版本信息出错()" + svne.getMessage());
			           
			        }	
						
				       display(filePath,changed_date,revision); //执行信息输出
				       //文件进度展示
				    	m=content_id;
				    	if (m%100==0) {
				    		System.out.println(m+".");
						}else{
							System.out.print(m+".");
						}
					   	
			}
		stmt01.close();
	} catch (Exception e1) {
		System.out.println("51~56行出错");e1.printStackTrace();
	}
    if(start==-1){
    	System.out.println("content_original 完成。文件在对应版本不存在的="+nonExistent+".文件是目录的="+isDir);
    }else {
		System.out.println("content_original 完成。已跳过："+t+"个(是文件夹或者数据库中已有该记录)");
	}
    conn.close();
    }
    
    public static void display(String filePath,String changed_date,long revision) {
        SVNProperties fileProperties = new SVNProperties();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //在这里指定要查看的文件的版本
        try {
			repository.getFile(filePath, revision, fileProperties, baos);
	        String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
	        boolean isTextType = SVNProperty.isTextMimeType(mimeType);
	        /* 显示文件属性信息.
	        		Iterator iterator = fileProperties.nameSet().iterator();
			        while (iterator.hasNext()) {
			            String propertyName = (String) iterator.next();
			            String propertyValue = fileProperties.getStringValue(propertyName);
			            System.out.println("File property->" + propertyName + "="
			                    + propertyValue);
			        }
			      */
	        //如果是文本类型，显示文件内容
	        
			try {
				String sql03="insert into content_original values (?,?,?,?,?,?)";
		        PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql03);
				ps.clearBatch();
		        
		        if (isTextType) {
		        	content_id++;
		        	ps.setInt(1, content_id);
		        	ps.setString(2, "/"+filePath);
		        	ps.setString(3, changed_date);
					ps.setLong(4, revision);
					ps.setInt(5, 1);										// System.out.println("File name:"+filePath02);
												          //  System.out.println("File contents:");
		            try {
						String str=baos.toString("UTF-8");
						ps.setString(6, str);
						ps.addBatch();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("155~162行出错");
					}
		            /* 原版可以输出文文件内容的代码
		            try {
		            	
		                baos.writeTo(System.out);
		            } catch (IOException ioe) {
		                ioe.printStackTrace();
		            }
		            */
		        } 
		        
		        else {
		        	content_id++;
		        	ps.setInt(1, content_id);
		        	ps.setString(2, "/"+filePath);
		        	ps.setString(3, changed_date);
					ps.setLong(4, revision);
					ps.setInt(5, 0);
					ps.setString(6, "此文件不是文本文件");
					ps.addBatch();
		           //System.out.println(filePath+":文件不是文本内容");
		        }
		        ps.executeBatch();
		        ps.close();
			} catch (SQLException e1) {
				System.err.println("sql执行出错:"+filePath+revision);
				e1.printStackTrace();
				System.exit(1);
			}
		} catch (SVNException e1) {
			e1.printStackTrace();
			System.out.println("查看文件版本时出错！正在重新尝试："+filePath+":"+revision);
			try {
    			Thread.sleep(5000);
    		} catch (Exception e3) {
    			System.out.println("线程暂停5秒错误");
    			e3.printStackTrace();
    			
    		}
			try {
	            SVNNodeKind nodeKind = repository.checkPath(filePath, revision);
	            if (nodeKind == SVNNodeKind.NONE) {
	            	System.err.println("二次检查不存在该文件，已跳过");
	            } else if (nodeKind == SVNNodeKind.DIR) {
	            	System.err.println("二次检查该文件是一个目录，已跳过");
	            }
	            else {
	        		display(filePath,changed_date,revision);	
				}
	        } catch (SVNException svne) {
	          //  System.err.println("查询文件与对应版本信息出错()" + svne.getMessage());
	            display(filePath,changed_date,revision);
	        }	
			System.out.println("重试成功，已存入数据库，文件名："+filePath);
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
        //SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
       // FSRepositoryFactory.setup();
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