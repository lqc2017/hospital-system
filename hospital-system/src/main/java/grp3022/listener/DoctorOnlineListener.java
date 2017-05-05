/**
 * 全琛
 * 2017年5月4日
 */
package grp3022.listener;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Queue;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;  

/**
 * @author 全琛
 *
 */
public class DoctorOnlineListener implements HttpSessionListener {
	/* Session创建事件 */  
	public void sessionCreated(HttpSessionEvent se) {  
		System.out.println("session createTime:"+new Date());
	}
	
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext ctx = se.getSession().getServletContext();
		HttpSession session = se.getSession();
		Map<String, Queue<Long>> patientQueue =  (Map<String, Queue<Long>>) ctx.getAttribute("patientQueue");
		
		Enumeration<String> sessEnum = session.getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String s = sessEnum.nextElement();
			System.out.print(s);
			System.out.println("==" + session.getAttribute(s));
		}
		if(session.getAttribute("doctorId")!=null){
			patientQueue.remove(se.getSession().getAttribute("doctorId").toString());
			System.out.println("会话超时，删患者队列");
		}
		System.out.println("session deleteTime:"+new Date());
	}
}
