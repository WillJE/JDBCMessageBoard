package com.imooc.jdbc.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;      

	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		userService = new UserService();
	}

	/**
	 * 销毁
	 */
	public void destroy() {
		userService = null;
	}

	/**
	 * 处理登录验证
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
								throws ServletException, IOException {
		//获取用户提交的账号密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.login(username, password);
		if(user != null) {													//验证成功
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/message/list.do").forward(request, response);						//回到留言板页面		
		} else {
			request.getRequestDispatcher("/login.do").forward(request, response);						//回到留言板页面
		}
	}

}
