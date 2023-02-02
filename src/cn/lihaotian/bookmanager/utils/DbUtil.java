package cn.lihaotian.bookmanager.utils;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
public class DbUtil {

	public Connection getConnection()throws Exception{
		String dbDriver = "com.mysql.jdbc.Driver";
		Class.forName(dbDriver);
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/bookmanager?useSSL=true&characterEncoding=utf-8";
		String dbUserName = "root";
		String dbPassword = "whoami";
		return (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
	}

	public void closeCon (Connection con)throws Exception {
		if(con!=null){
			con.close();
		}
	}
	
}
