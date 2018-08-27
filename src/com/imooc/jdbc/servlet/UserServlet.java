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
		System.out.println("初始化 userServlet");
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
		//System.out.println("userServlet访问");
		String pathName = request.getServletPath(); 										//获取servlet路径
		if("/userInfo.do".equals(pathName)) {														//查看用户信息
			request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response); 							//转发到用户信息页面
		} else if("/editUserPrompt.do".equals(pathName)) {
			long id = Long.valueOf(request.getParameter("id"));					//获得欲修改的id
			User user = (User) request.getAttribute("user");
		   /*System.out.println("跳转的id= " + id);
			User user = userService.getUserById(id);
			System.out.println("跳转后的id=" + user.getId());*/
			if(user != null) {
				System.err.println("跳转修改信息界面");
				request.setAttribute("user", user);	
				request.getRequestDispatcher("/WEB-INF/views/biz/edit_user.jsp").forward(request, response); 							//转发到修改用户信息页面			
			} else {
				request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response); 							//转发到用户页面
			}
		} else if ("/editUser.do".equals(pathName)) { 													//修改完用户信息
			//接受传递过来的参数
			 Long id = Long.valueOf(request.getParameter("id"));				 
			// System.out.println("servlet层id=" + id);
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
				System.out.println("格式化日期失败");
				e.printStackTrace();
			}
			 user.setPhone(phone);
			 user.setAddress(address);
			 boolean flag = userService.updateUser(user);    							//更新用户信息
			 if(flag) { 															//更新成功
				 request.getSession().setAttribute("user", user);
				 request.setAttribute("user", user);				
				 request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response); 							//转发到用户页面
			 } else {
				 request.getRequestDispatcher("/WEB-INF/views/biz/404.jsp").forward(request, response);
			 }			 
		} else {
			request.getRequestDispatcher("/WEB-INF/views/biz/404.jsp").forward(request, response);
		}
					
	}
	
	/*
	 * 转化时间格式
	 */
	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatStr = sdf.format(date);
		return formatStr;
	}

}
