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
	 * ��ʼ��
	 */
	public void init(ServletConfig config) throws ServletException {
		userService = new UserService();
	}

	/**
	 * ����
	 */
	public void destroy() {
		userService = null;
	}

	/**
	 * �����¼��֤
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
								throws ServletException, IOException {
		//��ȡ�û��ύ���˺�����
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.login(username, password);
		if(user != null) {													//��֤�ɹ�
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/message/list.do").forward(request, response);						//�ص����԰�ҳ��		
		} else {
			request.getRequestDispatcher("/login.do").forward(request, response);						//�ص����԰�ҳ��
		}
	}

}
