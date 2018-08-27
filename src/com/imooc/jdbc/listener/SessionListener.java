package com.imooc.jdbc.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * �ػ�������
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
  
	/**
     * ���ӷ�����
     */
    public void sessionCreated(HttpSessionEvent e)  { 
         ServletContext servletContext = e.getSession().getServletContext();
         Integer count = (Integer) servletContext.getAttribute("count");
         if(count == null) {
        	 count = 0;
         }
         count += 1;
         System.out.println("������: " + count);
         servletContext.setAttribute("count", count);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
