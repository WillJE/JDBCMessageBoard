package com.imooc.jdbc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/* 
 * �������ݿ�Ĺ����� (����ģʽ)
 */
public final class ConnectionUtil {

	private static String url = "jdbc:mysql://localhost:3306/message_board?characterEncoding=utf-8";
	private static String user = "root";
	private static String password = "root";
	
	private ConnectionUtil() {}
	
	static {
		try {
			//��������
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("���ݿ����������쳣");
			e.printStackTrace();
		}		
	}
	
	/*
	 * ��ȡ����
	 */
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("��ȡ����ʧ��");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * �ͷ���Դ
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
	 * �������ݿ��Ƿ���������
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
