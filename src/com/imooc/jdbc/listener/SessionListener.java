package com.imooc.jdbc.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 回话监听器
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
  
	/**
     * 增加访问量
     */
    public void sessionCreated(HttpSessionEvent e)  { 
         ServletContext servletContext = e.getSession().getServletContext();
         Integer count = (Integer) servletContext.getAttribute("count");
         if(count == null) {
        	 count = 0;
         }
         count += 1;
         System.out.println("访问量: " + count);
         servletContext.setAttribute("count", count);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
