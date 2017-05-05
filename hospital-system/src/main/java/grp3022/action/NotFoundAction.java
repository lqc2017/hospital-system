package grp3022.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@Action(value = "*", results = { @Result(name = "success", location = "/WEB-INF/errorpages/404.jsp") })
public class NotFoundAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String execute(){
		return "success";
	}
}
