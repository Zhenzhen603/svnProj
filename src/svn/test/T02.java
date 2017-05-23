package svn.test;

import java.sql.SQLException;

import svn.utils.Add_commit_date_filelogs;

import svn.jdk8.FileDiff;

public class T02 {
	static String svnurl = "svn://localhost/SVNKitRepository3";
	static String username = "admin";
	static String password = "admin";
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Add_commit_date_filelogs.add();
	}

}
