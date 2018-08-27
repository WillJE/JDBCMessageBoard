package com.imooc.jdbc.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.MessageService;

/**
 * 留言处理Servlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MessageService messageService;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();
		messageService = new MessageService();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		messageService = null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathName = request.getServletPath();
		if("/addMessagePrompt.do".equals(pathName)) {
			request.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(request, response);
		} else if ("/addMessage.do".equals(pathName)) {
			User user = (User) request.getSession().getAttribute("user");
			System.out.println("id=" + user.getId());
			if(user == null) {
				request.getRequestDispatcher("/message/list.do").forward(request, response);
			} else {
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				boolean result = messageService.addMessage(new Message(user.getId(),user.getUsername(),title,content));
				if(result) {											//添加成功
					request.getRequestDispatcher("/message/list.do").forward(request, response);
				} else {
					request.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(request, response);
				}
			}			
		}else {
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }	
	}

}
