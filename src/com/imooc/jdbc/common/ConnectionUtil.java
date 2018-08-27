package com.imooc.jdbc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/* 
 * 操作数据库的公共类 (单例模式)
 */
public final class ConnectionUtil {

	private static String url = "jdbc:mysql://localhost:3306/message_board?characterEncoding=utf-8";
	private static String user = "root";
	private static String password = "root";
	
	private ConnectionUtil() {}
	
	static {
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("数据库驱动加载异常");
			e.printStackTrace();
		}		
	}
	
	/*
	 * 获取连接
	 */
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("获取连接失败");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * 释放资源
	 */
	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		
		try {
			
			if(rs != null) { 
				rs.close();
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {	
			
			try {
				if(stmt !=null) {
					stmt.close();
				}
			} catch (SQLException e) { 
				e.printStackTrace();
			} finally {
				
				try {
					if (conn != null) {
	                   conn.close();
	                }
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			
		}	
	}





	/*
	 * 测试数据库是否正常连接
	 */
	public static void main(String[] args) {
		
		Connection conn = null;
		java.sql.Statement stmt = null;
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from user where id = 1";
		    ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println(rs.getString("username"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(null, stmt, conn);
		}
		
	}

}
