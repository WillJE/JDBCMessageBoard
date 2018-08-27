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
 * ��Ϣdao
 */
public class MessageDao {

	
	/*
	 * ��ҳ��ѯȫ������
	 * page ��ǰҳ��
	 * pageSize ÿҳ��¼��
	 */
	public List<Message> getMessages(int page, int pageSize) {
		
		String sql = "select * from message order by create_time desc limit ?,?";			//SQL���			
		
		return getMessagesBySQL(page, pageSize, sql);	
	}
	
	/*
	 * ��ѯ�ҵ�����
	 */
	public List<Message> getMyMessages(int page, int pageSize, long userId) {
			
			String sql = "select * from message  where user_id=" + userId + " order by create_time desc limit ?,?";			//SQL���			
			
			System.out.println(sql);
			
			return getMessagesBySQL(page, pageSize, sql);	
		}
	
	/*
	 * ��ѯ���Ե�ģ�巽��
	 */
	private List<Message> getMessagesBySQL(int page, int pageSize, String sql) {
		Connection conn = ConnectionUtil.getConnection();				//��ȡ����	
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
						rs.getTimestamp("create_time"))				//�˴�ʱ��Ĵ���ʽ�Ƚ���Ҫ
						);				
			}
		} catch (SQLException e) {
			System.out.println("��ѯ��Ϣʧ��");
			e.printStackTrace();
		} finally {
			ConnectionUtil.release(rs, stmt, conn);			
		}
		return messages;
		
	}
	
	/*
	 * ����������Ŀ
	 */
	public int countMessages() {
		Connection conn = ConnectionUtil.getConnection();
		String sql = "select count(*) as total from message";				//��ѯ��¼��Ŀ
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
	 * ����message
	 */
	public boolean save(Message message) {
		Connection conn = ConnectionUtil.getConnection();
		String sql = "insert message(user_id,username,title,content,create_time) values(?,?,?,?,?)";				//��ѯ��¼��Ŀ
		PreparedStatement stmt = null;
		boolean flag = false;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, message.getUserId());
			stmt.setString(2, message.getUsername());
			stmt.setString(3, message.getTitle());
			stmt.setString(4, message.getContent());
			stmt.setTimestamp(5, new Timestamp( message.getCreateTime().getTime()));								//ǰ����java.sql.Date
			System.out.println("dao��ʱ��" + message.getCreateTime());
			stmt.execute();
			flag = true;
		} catch (SQLException e) {
			System.out.println("���������쳣");
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			ConnectionUtil.release(null, stmt, conn);               								//�ͷ���Դ
		}
		
		return flag;
	}
}
