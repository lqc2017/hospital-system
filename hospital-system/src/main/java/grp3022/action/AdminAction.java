/**
 * 全琛
 * 2017年5月5日
 */
package grp3022.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalAdmin;
import grp3022.bean.HospitalAdminSo;
import grp3022.service.HospitalAccountService;
import grp3022.service.HospitalAdminService;
import grp3022.util.Context;

/**
 * @author 全琛ggss
 *
 */
@Namespace("/admin")
public class AdminAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private HospitalAccountService hospitalAccountService;
	@Resource
	private HospitalAdminService hospitalAdminService;
	
	private HospitalAdmin admin;
	private List<HospitalAdmin> admins;
	private HospitalAdminSo so;
	private Integer pn;

	@Action(value = "home", results = { @Result(name = "success", location = "/home/admin.jsp") })
	public String home() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		Long adminId = hospitalAccountService.getAIByUsername(Context.getUsername());
		hospitalAccountService.updateByUsername(Context.getUsername());
		setAdmin(hospitalAdminService.getRecordById(adminId));
		
		/*登陆判断*/
		if(session.get("adminId")!=null){
			System.out.println("管理员:"+this.getAdmin().getName()+"已登陆");
		}else {
			session.put("adminId", adminId);
			System.out.println("管理员:" + this.getAdmin().getName() + "上线");
		}
		return "success";
	}
	
	@Action(value = "staff", results = { @Result(name = "success", location = "/admin/staff.jsp") })
	public String staff() {
		return "success";
	}
	
	@Action(value = "list", results = { @Result(name = "success", location = "/admin/list.jsp") })
	public String list() {
		ActionContext ct = ActionContext.getContext();
		
		if(so==null){
			this.setSo(new HospitalAdminSo());
		}
		
		PageInfo<HospitalAdmin> pageInfo = hospitalAdminService.getPageBySo(so, pn, null);
		this.setAdmins(pageInfo.getList());
		
		ct.put("so", so);
		ct.put("pageInfo", pageInfo);
		return "success";
	}
	
	public HospitalAdmin getAdmin() {
		return admin;
	}
	
	public void setAdmin(HospitalAdmin admin) {
		this.admin = admin;
	}

	public HospitalAdminSo getSo() {
		return so;
	}

	public void setSo(HospitalAdminSo so) {
		this.so = so;
	}

	public List<HospitalAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<HospitalAdmin> admins) {
		this.admins = admins;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}
	
}
