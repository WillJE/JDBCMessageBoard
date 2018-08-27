package com.imooc.jdbc.service;

import java.util.Date;
import java.util.List;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.dao.MessageDao;

/*
 * ��Ϣservice
 */
public class MessageService {
	
	private MessageDao messageDao;

	/*
	 * ������
	 */
	public MessageService() {
		messageDao = new MessageDao();			//��ʼ��dao
	}
	
	/*
	 * ��ѯ����
	 */
	public List<Message> getMessages(int page, int pageSize) {
		return messageDao.getMessages(page, pageSize);
	}
	
	/*
	 * ��ѯ�ҵ�����
	 */
	public List<Message> getMyMessages(int page, int pageSize, long userId) {
		return messageDao.getMyMessages(page, pageSize, userId);
	}
	/*
	 * ������Ŀ
	 */
	public int countMessages() {
		return messageDao.countMessages();
	}
	
	/*
	 * ��������
	 */
	public boolean addMessage(Message message) {
		Date date = new Date();
		message.setCreateTime(date);
		System.out.println("���ڵ�ʱ����" + date);
		System.out.println("���ڵ�id��" + message.getUserId());
		return messageDao.save(message);
	} 
}
