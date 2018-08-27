package com.imooc.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.common.ConnectionUtil;


/*
 * 消息dao
 */
public class MessageDao {

	
	/*
	 * 分页查询全部留言
	 * page 当前页码
	 * pageSize 每页记录数
	 */
	public List<Message> getMessages(int page, int pageSize) {
		
		String sql = "select * from message order by create_time desc limit ?,?";			//SQL语句			
		
		return getMessagesBySQL(page, pageSize, sql);	
	}
	
	/*
	 * 查询我的留言
	 */
	public List<Message> getMyMessages(int page, int pageSize, long userId) {
			
			String sql = "select * from message  where user_id=" + userId + " order by create_time desc limit ?,?";			//SQL语句			
			
			System.out.println(sql);
			
			return getMessagesBySQL(page, pageSize, sql);	
		}
	
	/*
	 * 查询留言的模板方法
	 */
	private List<Message> getMessagesBySQL(int page, int pageSize, String sql) {
		Connection conn = ConnectionUtil.getConnection();				//获取连接	
		PreparedStatement stmt = null;
        ResultSet rs = null;
		List<Message> messages = new ArrayList<>();
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, (page - 1) * pageSize);					
			stmt.setInt(2, pageSize);			
			rs = stmt.executeQuery();					
			while (rs.next()) {
				messages.add(new Message(rs.getLong("id"),
						rs.getLong("user_id"),
						rs.getString("username"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("create_time"))				//此处时间的处理方式比较重要
						);				
			}
		} catch (SQLException e) {
			System.out.println("查询信息失败");
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn);			
		}
		return messages;
		
	}
	
	/*
	 * 计算留言数目
	 */
	public int countMessages() {
		Connection conn = ConnectionUtil.getConnection();
		String sql = "select count(*) as total from message";				//查询记录数目
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int num = 0;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn);
		}
		
		return num;
	}
	
	/*
	 * 增加message
	 */
	public boolean save(Message message) {
		Connection conn = ConnectionUtil.getConnection();
		String sql = "insert message(user_id,username,title,content,create_time) values(?,?,?,?,?)";				//查询记录数目
		PreparedStatement stmt = null;
		boolean flag = false;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, message.getUserId());
			stmt.setString(2, message.getUsername());
			stmt.setString(3, message.getTitle());
			stmt.setString(4, message.getContent());
			stmt.setTimestamp(5, new Timestamp( message.getCreateTime().getTime()));								//前面是java.sql.Date
			System.out.println("dao层时间" + message.getCreateTime());
			stmt.execute();
			flag = true;
		} catch (SQLException e) {
			System.out.println("更新留言异常");
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			ConnectionUtil.release(null, stmt, conn);               								//释放资源
		}
		
		return flag;
	}
}
