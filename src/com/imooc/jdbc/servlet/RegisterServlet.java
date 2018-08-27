package com.imooc.jdbc.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.UserService;

/**
 * Servlet implementation class Register
 */
@WebServlet(description = "ע��", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserService userService = null;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		userService = new UserService();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		//��ȡע����洫�ݹ����Ĳ���
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String realName = request.getParameter("realName");
		//��ʽ������
		String birthday = request.getParameter("birthday");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date  date = null;
		try {
			date =  sdf.parse(birthday);
		} catch (ParseException e) {
			System.out.println("ע��-��ʽ������ʧ��");
			e.printStackTrace();
		}		
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		user.setUsername(username);
		user.setPassword(password);
		user.setRealName(realName);
		user.setBirthday(date);
		user.setPhone(phone);
		user.setAddress(address);
		//�����û�
	    boolean flag = userService.addUser(user);
		//session������û���Ϣ
	    if(flag) {	    	
	    	request.getSession().setAttribute("user", user);
	    } else {
			System.out.println("����û�ʧ��");
		}
		//ת������ҳ
		request.getRequestDispatcher("/message/list.do").forward(request, response);
	}

}
