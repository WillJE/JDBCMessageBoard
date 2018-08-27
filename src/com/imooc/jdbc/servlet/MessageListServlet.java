package com.imooc.jdbc.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.MessageService;

/**
 * 消息列表servlet
 */
@WebServlet(description = "消息列表servlet", urlPatterns = { "/MessageListServlet" })
public class MessageListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private MessageService messageService;
  
	/**
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();
		messageService = new MessageService();
	}

	/**
	 */
	public void destroy() {
		super.destroy();
		messageService = null;
	}
	
	/*
	 * 	(non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	 @Override
	 protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();														//servlet路径
	   String pageStr = request.getParameter("page");				    
	   int page = 1;					//当前页码
	   if(pageStr != null && (!"".equals(pageStr))) {
		   try {
               page = Integer.parseInt(pageStr);
           } catch (NumberFormatException e) {
               e.printStackTrace();
           }
	   }
	   
	   int pageSize = 5;				//每页大小	   
	   int count = messageService.countMessages();				//留言数量	   
	   List<Message> messages = null;
	   if("/message/list.do".equals(path)) {
		   messages = messageService.getMessages(page, pageSize);
	   } else if ("/my/message/list.do".equals(path)) {
		   HttpSession session = request.getSession();
		   User user = (User) session.getAttribute("user");
		   if(user == null) {
			   request.getRequestDispatcher("/WEB-INF/views/biz/login.jsp").forward(request, response);
		   }
		   long id = user.getId();
		   messages = messageService.getMyMessages(page, pageSize, id);
	}
	   int last = count % 5 == 0 ? (count / 5) : ((count / 5) + 1);					//尾页
	   request.setAttribute("last", last);
	   request.setAttribute("page", page);
	   request.setAttribute("messages", messages);
	   request.getRequestDispatcher("/WEB-INF/views/biz/message_list.jsp").forward(request, response);
	   
	 }

}
