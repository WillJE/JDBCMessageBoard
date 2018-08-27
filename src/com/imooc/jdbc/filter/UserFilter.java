package com.imooc.jdbc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.imooc.jdbc.bean.User;

/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter(filterName = "UserFiler", description = "用户过滤器", urlPatterns = { "/UserFiler" })
public class UserFilter implements Filter {

   

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		User user = (User) ((HttpServletRequest)request).getSession().getAttribute("user");
		request.setAttribute("user", user);
		// pass the request along the filter chain
		chain.doFilter(request, response);						//通过过滤器
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
