package grp3022.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectCheck;
import grp3022.service.HospitalProjectService;
import grp3022.service.HospitalProjectCheckService;

@Namespace("/project_check")
@ParentPackage("json-default")
public class ProjectCheckAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private HospitalProjectCheckService hospitalProjectCheckService;
	@Resource
	private HospitalProjectService hospitalProjectService;

	private List<Long> projectIds;
	private List<Long> projectCheckIds;
	private List<HospitalProjectCheck> projectChecks;
	private HospitalProjectCheck projectCheck;
	private Long projectCheckId;
	private String result;

	@Action(value = "multi_edit", results = { @Result(name = "success", location = "/project_check/multi_edit.jsp") })
	public String multiEdit() {

		List<HospitalProject> projects = new ArrayList<HospitalProject>();
		for (Long id : projectIds)
			projects.add(hospitalProjectService.getRecordById(id));

		ActionContext ct = ActionContext.getContext();
		ct.put("projects", projects);
		return "success";
	}

	@Action(value = "multi_add", results = { @Result(name = "success", type = "json") })
	public String multiAdd() {
		this.setProjectCheckIds(hospitalProjectCheckService.addRecords(projectChecks));
		Map<String, Object> session = ActionContext.getContext().getSession();

		@SuppressWarnings("unchecked")
		List<Long> sessionIds = (List<Long>) session.get("projectCheckIds");
		if (sessionIds.size() == 0) {
			System.out.println("没有已添加的检查");
		}
		for (Long id : projectCheckIds) {
			sessionIds.add(id);
		}
		session.put("projectCheckIds", sessionIds);

		return "success";
	}

	@Action(value = "update", results = { @Result(name = "success", type = "json") })
	public String update() {
		hospitalProjectCheckService.updateRecordById(projectCheck);
		return "success";
	}

	@Action(value = "delete", results = { @Result(name = "success", type = "json") })
	public String delete() {
		try {
			hospitalProjectCheckService.deleteRecordById(projectCheckId);
			Map<String, Object> session = ActionContext.getContext().getSession();
			// 更新session
			@SuppressWarnings("unchecked")
			List<Long> hpsIds = (List<Long>) session.get("projectCheckIds");
			if (hpsIds.contains(projectCheckId)) {
				Iterator<Long> it = hpsIds.iterator();
				while (it.hasNext()) {
					Long hpcId = it.next();
					if (hpcId.longValue() == projectCheckId.longValue()) {
						it.remove();
					}
				}
			}
			session.put("projectCheckIds", hpsIds);
		} catch (Exception e) {
			this.setResult("fail");
		}
		this.setResult("success");
		return "success";
	}

	@Action(value = "edit", results = { @Result(name = "success", location = "/project_check/edit.jsp") })
	public String edit() {
		this.setProjectCheck(hospitalProjectCheckService.getRecordById(projectCheckId));
		HospitalProject project = hospitalProjectService.getRecordById(projectCheck.getProjectId());
		ActionContext ct = ActionContext.getContext();
		ct.put("project", project);
		return "success";
	}

	@JSON(serialize = false)
	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}

	public List<HospitalProjectCheck> getProjectChecks() {
		return projectChecks;
	}

	public void setProjectChecks(List<HospitalProjectCheck> projectChecks) {
		this.projectChecks = projectChecks;
	}

	public HospitalProjectCheck getProjectCheck() {
		return projectCheck;
	}

	public void setProjectCheck(HospitalProjectCheck projectCheck) {
		this.projectCheck = projectCheck;
	}

	public Long getProjectCheckId() {
		return projectCheckId;
	}

	public void setProjectCheckId(Long projectCheckId) {
		this.projectCheckId = projectCheckId;
	}

	public List<Long> getProjectCheckIds() {
		return projectCheckIds;
	}

	public void setProjectCheckIds(List<Long> projectCheckIds) {
		this.projectCheckIds = projectCheckIds;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
