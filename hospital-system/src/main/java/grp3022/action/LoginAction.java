package grp3022.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalAccount;

public class LoginAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	HospitalAccount ha;
	
	@Action(value = "login", results = { @Result(name = "success", location = "/login.jsp") })
	public String login(){
		return "success";
	}
	
	@Action(value="logout", results = { @Result(name = "success",type="redirectAction",location = "login?logout")})
	public String logoutPage () {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			SecurityContextLogoutHandler sclh = new SecurityContextLogoutHandler();
			sclh.setInvalidateHttpSession(false);
			sclh.logout(request, response, auth);
		}
		return "success";
	}

	/*@SuppressWarnings("unused")
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}*/

}
