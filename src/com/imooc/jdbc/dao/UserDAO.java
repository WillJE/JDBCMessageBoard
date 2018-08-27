package com.imooc.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.common.ConnectionUtil;

/*
 * �û�Dao
 */
public class UserDAO {

	/*
	 * ��¼��֤
	 */
	public User login(String username, String password) {
		Connection conn = ConnectionUtil.getConnection();										//�������
		User user = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from user where username = ? and password = ?";								//��ѯ���
		try {
			stmt = conn.prepareStatement(sql);							
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();								//��ѯ
			if(rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));				
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRealName(rs.getString("real_name"));
				user.setBirthday(rs.getDate("birthday"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			System.out.println("��¼ʧ��");
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn);  										//�ͷ�����
		}
		
		return user;
	}
	
	/*
	 * ����ID��ȡ�û�
	 */
	public User getUserById(long id) {
		//System.out.println("Dao�㴫������id=" + id);
		User user = null;
		Connection conn = ConnectionUtil.getConnection();						//�������
		String sql = "select * from user where id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);	
			stmt.setLong(1, id);
			rs = stmt.executeQuery();										//��ѯ
			while (rs.next()) {
				user = new User();
				user.setId(id);
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRealName(rs.getString("real_name"));
				user.setBirthday(rs.getDate("birthday"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			System.out.println("��ѯ�û�ʧ��");
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn); 								//�ͷ���Դ
		}
				
		return user;
	}
	
	/*
	 * �޸��û���Ϣ
	 */
	public boolean updateUser(User user) {
		boolean flag = true;
		Connection conn = ConnectionUtil.getConnection();							//��ȡ����
		String sql = "update user set username=?,password=?,real_name=?,birthday=?,phone=?,address=?  where id=?";
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println(conn.getAutoCommit());
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getRealName());
			stmt.setDate(4, new Date(user.getBirthday().getTime())); 			//ǰ��Date���Ͳ�һ��
			stmt.setString(5, user.getPhone());
			stmt.setString(6, user.getAddress());
			stmt.setLong(7, user.getId());
			System.err.println("Dao��Id:"  + user.getId());
			int num = stmt.executeUpdate();														//ִ��SQL���
			if(num > 0) {
				System.out.println("�����û���Ϣ�ɹ�");
			} else {
				System.out.println("�����û���ϢʧЧ");
			}
		} catch (SQLException e) {
			System.out.println("�����û���Ϣʧ��");
			e.printStackTrace();
			flag = false;
		} finally {
			ConnectionUtil.release(null, stmt, conn);
		}
		return flag;	
	}
	
	/*
	 * �����û�
	 */
	public boolean addUser(User user) {
		boolean flag = true;
		Connection conn = ConnectionUtil.getConnection();							//��ȡ����
		String sql = "insert into user(username,password,real_name,birthday,phone,address) values(?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		try {
			
			stmt =  conn.prepareStatement(sql);					
			//���ò���
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getRealName());
			Timestamp timestamp = new Timestamp(user.getBirthday().getTime());
			stmt.setTimestamp(4, timestamp);
			stmt.setString(5, user.getPhone());
			stmt.setString(6, user.getAddress());
			
			int num = stmt.executeUpdate();
			if(num > 0) {
				flag = true;
			} else {
				flag = false;
				System.out.println("�����û�δ�ɹ�");
			}
		} catch (SQLException e) {
			System.out.println("�����û��쳣");
			e.printStackTrace();			
		} finally {
			ConnectionUtil.release(null, stmt, conn);
		}
		
		return flag;
	}
}
