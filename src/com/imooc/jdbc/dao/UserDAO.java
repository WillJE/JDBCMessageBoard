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
 * 用户Dao
 */
public class UserDAO {

	/*
	 * 登录验证
	 */
	public User login(String username, String password) {
		Connection conn = ConnectionUtil.getConnection();										//获得连接
		User user = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from user where username = ? and password = ?";								//查询语句
		try {
			stmt = conn.prepareStatement(sql);							
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();								//查询
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
			System.out.println("登录失败");
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn);  										//释放连接
		}
		
		return user;
	}
	
	/*
	 * 根据ID获取用户
	 */
	public User getUserById(long id) {
		//System.out.println("Dao层传递来的id=" + id);
		User user = null;
		Connection conn = ConnectionUtil.getConnection();						//获得连接
		String sql = "select * from user where id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);	
			stmt.setLong(1, id);
			rs = stmt.executeQuery();										//查询
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
			System.out.println("查询用户失败");
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn); 								//释放资源
		}
				
		return user;
	}
	
	/*
	 * 修改用户信息
	 */
	public boolean updateUser(User user) {
		boolean flag = true;
		Connection conn = ConnectionUtil.getConnection();							//获取连接
		String sql = "update user set username=?,password=?,real_name=?,birthday=?,phone=?,address=?  where id=?";
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println(conn.getAutoCommit());
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getRealName());
			stmt.setDate(4, new Date(user.getBirthday().getTime())); 			//前后Date类型不一样
			stmt.setString(5, user.getPhone());
			stmt.setString(6, user.getAddress());
			stmt.setLong(7, user.getId());
			System.err.println("Dao层Id:"  + user.getId());
			int num = stmt.executeUpdate();														//执行SQL语句
			if(num > 0) {
				System.out.println("更新用户信息成功");
			} else {
				System.out.println("更新用户信息失效");
			}
		} catch (SQLException e) {
			System.out.println("更新用户信息失败");
			e.printStackTrace();
			flag = false;
		} finally {
			ConnectionUtil.release(null, stmt, conn);
		}
		return flag;	
	}
	
	/*
	 * 增加用户
	 */
	public boolean addUser(User user) {
		boolean flag = true;
		Connection conn = ConnectionUtil.getConnection();							//获取连接
		String sql = "insert into user(username,password,real_name,birthday,phone,address) values(?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		try {
			
			stmt =  conn.prepareStatement(sql);					
			//设置参数
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
				System.out.println("增加用户未成功");
			}
		} catch (SQLException e) {
			System.out.println("增加用户异常");
			e.printStackTrace();			
		} finally {
			ConnectionUtil.release(null, stmt, conn);
		}
		
		return flag;
	}
}
