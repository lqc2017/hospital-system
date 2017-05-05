package grp3022.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalHospitalization;
import grp3022.bean.HospitalHospitalizationSo;
import grp3022.bean.HospitalPatient;
import grp3022.service.HospitalAccountService;
import grp3022.service.HospitalHospitalizationService;
import grp3022.service.HospitalPatientService;

@Namespace("/hospitalization")
@ParentPackage("json-default")
public class HospitalizationAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private HospitalAccountService hospitalAccountService;
	@Resource
	private HospitalHospitalizationService hospitalHospitalizationService;
	@Resource
	private HospitalPatientService hospitalPatientService;

	private List<HospitalHospitalization> hospitalizations;
	private HospitalHospitalizationSo so;
	private HospitalHospitalization hospitalization;
	private Integer pn;
	private Long patientId;
	private Long hospitalizationId;
	private String result;
	private String message;

	@Action(value = "list", results = { @Result(name = "list", location = "/hospitalization/list.jsp") })
	public String list() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		// 初始化
		if (this.getSo() == null)
			setSo(new HospitalHospitalizationSo());
		
		Long doctorId = Long.valueOf(session.get("doctorId").toString());
		if(doctorId!=null)
			so.setDoctorId(doctorId);
		else
			return "false";
		
		PageInfo<HospitalHospitalization> pageInfo = hospitalHospitalizationService.getPageBySo(so, pn, null);
		setHospitalizations(pageInfo.getList());
		
		List<HospitalPatient> patients = new ArrayList<HospitalPatient>();
		for (HospitalHospitalization hospitalization : pageInfo.getList()) {
			Long patientId = hospitalization.getPatientId();
			patients.add(hospitalPatientService.getRecordById(patientId));
		}
		
		ct.put("so", so);
		ct.put("patients", patients);
		ct.put("pageInfo", pageInfo);
		return "list";
	}
	
	@Action(value = "patient_list", results = { @Result(name = "success", location = "/hospitalization/patient_list.jsp") })
	public String patientList() {
		if (this.getSo() == null)
			setSo(new HospitalHospitalizationSo());
		if(so.getCreateMonth()==null)
			so.setCreateMonth(new Date());
		PageInfo<HospitalHospitalization> pageInfo = hospitalHospitalizationService.getPageBySo(so, pn, 5);
		this.setHospitalizations(pageInfo.getList());
		ActionContext ct = ActionContext.getContext();
		
		ct.put("pageInfo", pageInfo);
		ct.put("so", so);
		return "success";
	}
	
	@Action(value = "doctor_list", results = { @Result(name = "success", location = "/hospitalization/doctor_list.jsp") })
	public String doctorList() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		if (this.getSo() == null)
			so = new HospitalHospitalizationSo();

		Long doctorId = Long.valueOf(session.get("doctorId").toString());
		if(doctorId!=null)
			so.setDoctorId(doctorId);
		else
			return "false";
		
		so.setCreateTime(new Date());
		PageInfo<HospitalHospitalization> pageInfo = hospitalHospitalizationService.getPageBySo(so,pn,5);
		hospitalizations = pageInfo.getList();
		System.out.println(hospitalizations.size());
		
		ct.put("pageInfo", pageInfo);
		ct.put("so", so);
		return "success";
	}
	
	@Action(value = "detail", results = { @Result(name = "detail", location = "/hospitalization/detail.jsp") })
	public String hospitalizationDetail() {
		ActionContext ct = ActionContext.getContext();
		HospitalHospitalization hh = hospitalHospitalizationService.getRecordById(hospitalizationId);
		
		ct.put("hh", hh);
		return "detail";
	}
	
	@Action(value = "confirm", results = {@Result(name = "success", type = "json") })
	public String hospitalizationConfirm() {
		try {
			HospitalHospitalization hh = hospitalHospitalizationService.getRecordById(hospitalizationId);
			hh.setIsLeave((short) 1);
			hh.setLeaveTime(new Date());
			hospitalHospitalizationService.updateRecordById(hh);
			
			result = "success";
		} catch (Exception e) {
			result = "fail";
			message = e.getMessage();
		}
		return "success";
	}
	
	@Action(value = "edit", results = { @Result(name = "success", location = "/hospitalization/edit.jsp") })
	public String hospitalizationEdit() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		HospitalPatient patient = hospitalPatientService.getRecordById(patientId);
		Long doctorId = Long.valueOf(session.get("doctorId").toString());
		ct.put("doctorId", doctorId);
		ct.put("patient", patient);
		return "success";
	}
	
	@Action(value = "add", results = { @Result(name = "success",type="json")})
	public String hospitalizationAdd() {
		try {
			hospitalizationId = hospitalHospitalizationService.addRecord(hospitalization);
			result = "success";
		} catch (Exception ex) {
			result = "fail";
			message = ex.getMessage();
		}
		return "success";
	}
	
	public List<HospitalHospitalization> getHospitalizations() {
		return hospitalizations;
	}

	public void setHospitalizations(List<HospitalHospitalization> hospitalizations) {
		this.hospitalizations = hospitalizations;
	}

	public HospitalHospitalizationSo getSo() {
		return so;
	}

	public void setSo(HospitalHospitalizationSo so) {
		this.so = so;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Long getHospitalizationId() {
		return hospitalizationId;
	}

	public void setHospitalizationId(Long hospitalizationId) {
		this.hospitalizationId = hospitalizationId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public HospitalHospitalization getHospitalization() {
		return hospitalization;
	}

	public void setHospitalization(HospitalHospitalization hospitalization) {
		this.hospitalization = hospitalization;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
