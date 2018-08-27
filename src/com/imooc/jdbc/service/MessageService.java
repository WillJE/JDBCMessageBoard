package com.imooc.jdbc.service;

import java.util.Date;
import java.util.List;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.dao.MessageDao;

/*
 * 消息service
 */
public class MessageService {
	
	private MessageDao messageDao;

	/*
	 * 构造器
	 */
	public MessageService() {
		messageDao = new MessageDao();			//初始化dao
	}
	
	/*
	 * 查询留言
	 */
	public List<Message> getMessages(int page, int pageSize) {
		return messageDao.getMessages(page, pageSize);
	}
	
	/*
	 * 查询我的留言
	 */
	public List<Message> getMyMessages(int page, int pageSize, long userId) {
		return messageDao.getMyMessages(page, pageSize, userId);
	}
	/*
	 * 留言数目
	 */
	public int countMessages() {
		return messageDao.countMessages();
	}
	
	/*
	 * 增加留言
	 */
	public boolean addMessage(Message message) {
		Date date = new Date();
		message.setCreateTime(date);
		System.out.println("现在的时间是" + date);
		System.out.println("现在的id是" + message.getUserId());
		return messageDao.save(message);
	} 
}
