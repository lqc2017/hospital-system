package grp3022.action;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectSo;
import grp3022.bean.HospitalRecord;
import grp3022.service.HospitalProjectService;
import grp3022.service.HospitalProjectCheckService;

@Namespace("/project")
@ParentPackage("json-default")
public class ProjectAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private HospitalProjectService hospitalProjectService;
	@Resource
	private HospitalProjectCheckService hospitalProjectCheckService;

	private PageInfo<HospitalProject> pageInfo;
	private HospitalRecord record;
	private HospitalProjectSo so;
	private HospitalProject project;
	private Long projectId;
	private String result;
	private Integer pn;
	
	@Action(value = "list", results = { @Result(name = "success", location = "/project/list.jsp") })
	public String list() {
		if (this.getSo() == null)
			this.setSo(new HospitalProjectSo());
		ActionContext ct = ActionContext.getContext();
		PageInfo<HospitalProject> pageInfo = hospitalProjectService.getPageBySo(so, pn, 10);
		
		this.setPageInfo(pageInfo);
		ct.put("so", so);
		return "success";
	}
	
	@Action(value = "edit", results = { @Result(name = "success", location = "/project/edit.jsp") })
	public String edit() {
		project = hospitalProjectService.getRecordById(projectId);
		return "success";
	}
	
	@Action(value = "update", results = { @Result(name = "success", type = "json") })
	public String update() {
		try{
			hospitalProjectService.updateRecordById(project);
			result = "success";
		}catch(Exception e){
			result = "fail";
		}
		return "success";
	}
	
	@Action(value = "delete", results = { @Result(name = "success",type="json")})
	public String delete() {
		try{
			hospitalProjectService.deleteRecordById(projectId);
			result = "success";
		}catch(Exception e){
			result = "fail";
		}
		return "success";
	}

	@Action(value = "select", results = { @Result(name = "list", location = "/project/select.jsp") })
	public String select() {
		if (this.getSo() == null)
			this.setSo(new HospitalProjectSo());

		PageInfo<HospitalProject> pageInfo = hospitalProjectService.getPageBySo(so, pn, 8);
		List<Long> selectedProjectIds = new ArrayList<Long>();

		ActionContext ct = ActionContext.getContext();
		Map<String, Object> session = ct.getSession();
		session.put("record", this.getRecord());
		
		@SuppressWarnings("unchecked")
		List<Long> projectCheckIds = (List<Long>) session.get("projectCheckIds");
		for (Long id : projectCheckIds) {
			selectedProjectIds.add(hospitalProjectCheckService.getRecordById(id).getProjectId());
		}
		this.setPageInfo(pageInfo);
		ct.put("spi", selectedProjectIds);
		ct.put("so", so);

		return "list";
	}

	@Action(value = "change_table", results = { @Result(name = "success", type = "json") })
	public String changeTable() {
		PageInfo<HospitalProject> pageInfo = hospitalProjectService.getPageBySo(so, pn, 8);

		this.setPageInfo(pageInfo);
		return "success";
	}
	
	public String formatCurrency(double s) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
		return currencyFormat.format(s);
	}

	public HospitalProjectSo getSo() {
		return so;
	}

	public void setSo(HospitalProjectSo so) {
		this.so = so;
	}

	public PageInfo<HospitalProject> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<HospitalProject> pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public HospitalRecord getRecord() {
		return record;
	}

	public void setRecord(HospitalRecord record) {
		this.record = record;
	}

	public HospitalProject getProject() {
		return project;
	}

	public void setProject(HospitalProject project) {
		this.project = project;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
