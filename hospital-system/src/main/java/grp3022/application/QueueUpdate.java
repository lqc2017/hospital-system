/**
 * 全琛
 * 2017年4月24日
 */
package grp3022.application;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author 全琛
 *
 */
public class QueueUpdate {
	/**
	 * @author 全琛
	 * @time 2017年4月24日 下午2:56:19
	 */
	@SuppressWarnings({ "unchecked"})
	public static final void update(ServletContext application,HttpSession session) {
		Map<String, Queue<Long>> patientQueue = (Map<String, Queue<Long>>) application.getAttribute("patientQueue");

		
		/*System.out.println("遍历前");
		Enumeration<String> sessEnum = session.getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String s = sessEnum.nextElement();
			System.out.println(s);
			System.out.println("==" + session.getAttribute(s));
		}
		System.out.println("遍历后")*/;
		
		if (patientQueue.size() == 0) {
			System.out.println("没有在线的医生");
		}
		if(session.getAttribute("doctorId")!=null){
			String doctorId = session.getAttribute("doctorId").toString();
			System.out.println("医生id：" + doctorId+"下线");
			patientQueue.remove(doctorId);
			
			//show(patientQueue);
		}
		session.removeAttribute("doctorId");
	}
	
	@SuppressWarnings("rawtypes")
	public static void show(Map<String, Queue<Long>> onlineDoctors) {
		Iterator iter = onlineDoctors.entrySet().iterator();
		System.out.println("当前在线医生ID————————");
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			System.out.println("医生id：" + key.toString());
		}
	}
}
