package com.imooc.jdbc.service;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.dao.UserDAO;
import com.sun.org.apache.regexp.internal.REUtil;

/*
 * �û�����
 */
public class UserService {
	private UserDAO userDAO;

	public UserService() {		
		userDAO = new UserDAO();
	}
	
	/*
	 * �û���¼
	 */
	public User login(String username, String password) {
		return userDAO.login(username, password);
	}
	
	/*
	 * ����id��ȡ�û���Ϣ
	 */
	public User getUserById(Long id) {
		return userDAO.getUserById(id);
	}
	
	/*
	 * �����û���Ϣ
	 */
	public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
	
	/*
	 * �����û���Ϣ
	 */
	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}
}
