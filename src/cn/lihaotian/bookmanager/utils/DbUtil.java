package cn.lihaotian.bookmanager.utils;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
public class DbUtil {

	public Connection getConnection()throws Exception{
		String dbDriver = "com.mysql.jdbc.Driver";
		Class.forName(dbDriver);
		String dbUrl = "jdbc:mysql://39.107.66.188:3307/bookmanager?useSSL=true&characterEncoding=utf-8";
		String dbUserName = "root";
		String dbPassword = "root";
		return (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
	}

	public void closeCon (Connection con)throws Exception {
		if(con!=null){
			con.close();
		}
	}
	
}
