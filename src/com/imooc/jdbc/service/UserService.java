package com.imooc.jdbc.service;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.dao.UserDAO;
import com.sun.org.apache.regexp.internal.REUtil;

/*
 * 用户服务
 */
public class UserService {
	private UserDAO userDAO;

	public UserService() {		
		userDAO = new UserDAO();
	}
	
	/*
	 * 用户登录
	 */
	public User login(String username, String password) {
		return userDAO.login(username, password);
	}
	
	/*
	 * 根据id获取用户信息
	 */
	public User getUserById(Long id) {
		return userDAO.getUserById(id);
	}
	
	/*
	 * 更新用户信息
	 */
	public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
	
	/*
	 * 增加用户信息
	 */
	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}
}
