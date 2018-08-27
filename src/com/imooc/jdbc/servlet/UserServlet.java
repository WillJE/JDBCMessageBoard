package com.imooc.jdbc.servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(description = "userServlet", urlPatterns = { "/UserServlet" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;	

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("��ʼ�� userServlet");
		userService = new UserService();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		userService = null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("userServlet����");
		String pathName = request.getServletPath(); 										//��ȡservlet·��
		if("/userInfo.do".equals(pathName)) {														//�鿴�û���Ϣ
			request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response); 							//ת�����û���Ϣҳ��
		} else if("/editUserPrompt.do".equals(pathName)) {
			long id = Long.valueOf(request.getParameter("id"));					//������޸ĵ�id
			User user = (User) request.getAttribute("user");
		   /*System.out.println("��ת��id= " + id);
			User user = userService.getUserById(id);
			System.out.println("��ת���id=" + user.getId());*/
			if(user != null) {
				System.err.println("��ת�޸���Ϣ����");
				request.setAttribute("user", user);	
				request.getRequestDispatcher("/WEB-INF/views/biz/edit_user.jsp").forward(request, response); 							//ת�����޸��û���Ϣҳ��			
			} else {
				request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response); 							//ת�����û�ҳ��
			}
		} else if ("/editUser.do".equals(pathName)) { 													//�޸����û���Ϣ
			//���ܴ��ݹ����Ĳ���
			 Long id = Long.valueOf(request.getParameter("id"));				 
			// System.out.println("servlet��id=" + id);
			 String username = request.getParameter("name");
			 String password = request.getParameter("password");
			 String realName = request.getParameter("realName");
			 String birthday = request.getParameter("birthday");
			 String phone = request.getParameter("phone");
			 String address = request.getParameter("address");				 
			 User user = new User();
			 user.setId(id);
			 user.setUsername(username);
			 user.setPassword(password);
			 user.setRealName(realName);
			 try {
				user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
			} catch (ParseException e) {
				System.out.println("��ʽ������ʧ��");
				e.printStackTrace();
			}
			 user.setPhone(phone);
			 user.setAddress(address);
			 boolean flag = userService.updateUser(user);    							//�����û���Ϣ
			 if(flag) { 															//���³ɹ�
				 request.getSession().setAttribute("user", user);
				 request.setAttribute("user", user);				
				 request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response); 							//ת�����û�ҳ��
			 } else {
				 request.getRequestDispatcher("/WEB-INF/views/biz/404.jsp").forward(request, response);
			 }			 
		} else {
			request.getRequestDispatcher("/WEB-INF/views/biz/404.jsp").forward(request, response);
		}
					
	}
	
	/*
	 * ת��ʱ���ʽ
	 */
	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatStr = sdf.format(date);
		return formatStr;
	}

}
