/**
 * 全琛
 * 2017年4月24日
 */
package grp3022.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * @author 全琛
 *
 */
public class MyContextLoaderListener extends ContextLoaderListener{
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Map<String,Queue<Long>> patientQueue = new HashMap<String,Queue<Long>>();
		System.out.println("初始化patientQueue!");
		event.getServletContext().setAttribute("patientQueue", patientQueue);
		super.contextInitialized(event);
	}
}
