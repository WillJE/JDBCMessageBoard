package com.imooc.jdbc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginPromptServlet
 */
@WebServlet(description = "��¼", urlPatterns = { "/LoginPromptServlet" })
public class LoginPromptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginPromptServlet() {
        super();
        System.out.println("loginPromptServlet����");
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		request.getRequestDispatcher("/WEB-INF/views/biz/login.jsp").forward(request, response);
	}

}
