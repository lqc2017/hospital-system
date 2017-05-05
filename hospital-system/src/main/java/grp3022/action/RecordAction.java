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

import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalPatient;
import grp3022.bean.HospitalPrescription;
import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectCheck;
import grp3022.bean.HospitalRecord;
import grp3022.bean.HospitalRecordSo;
import grp3022.service.HospitalAccountService;
import grp3022.service.HospitalMedicineService;
import grp3022.service.HospitalPatientService;
import grp3022.service.HospitalPrescriptionService;
import grp3022.service.HospitalProjectCheckService;
import grp3022.service.HospitalProjectService;
import grp3022.service.HospitalRecordService;

@Namespace("/record")
@ParentPackage("json-default")
public class RecordAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private HospitalAccountService hospitalAccountService;
	@Resource
	private HospitalPatientService hospitalPatientService;
	@Resource
	private HospitalRecordService hospitalRecordService;
	@Resource
	private HospitalMedicineService hospitalMedicineService;
	@Resource
	private HospitalPrescriptionService hospitalPrescriptionService;
	@Resource
	private HospitalProjectService hospitalProjectService;
	@Resource
	private HospitalProjectCheckService hospitalProjectCheckService;

	private List<HospitalRecord> records;
	private HospitalRecord record;
	private HospitalRecordSo so;
	private Integer pn;
	private Long recordId;
	private Long patientId;
	private List<Long> prescriptionIds;
	private List<Long> projectCheckIds;
	private String result;
	private String message;

	@Action(value = "list", results = { @Result(name = "success", location = "/record/list.jsp") })
	public String recordList() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		// 初始化
		if (this.getSo() == null)
			setSo(new HospitalRecordSo());
		
		Long doctorId = Long.valueOf(session.get("doctorId").toString());
		if(doctorId!=null)
			so.setDoctorId(doctorId);
		else
			return "false";
		PageInfo<HospitalRecord> pageInfo = hospitalRecordService.getPageBySo(so, pn, null);
		
		this.setRecords(pageInfo.getList());
		List<HospitalPatient> patients = new ArrayList<HospitalPatient>();
		for (HospitalRecord record : pageInfo.getList()) {
			Long patientId = record.getPatientId();
			patients.add(hospitalPatientService.getRecordById(patientId));
		}
		ct.put("so", so);
		ct.put("patients", patients);
		ct.put("pageInfo", pageInfo);
		return "success";
	}
	
	@Action(value = "patient_list", results = { @Result(name = "success", location = "/record/patient_list.jsp") })
	public String patientList() {
		System.out.println(pn);
		if (this.getSo() == null)
			setSo(new HospitalRecordSo());
		if(so.getCreateMonth()==null)
			so.setCreateMonth(new Date());

		PageInfo<HospitalRecord> pageInfo = hospitalRecordService.getPageBySo(so,pn,5);
		this.setRecords(pageInfo.getList());
		ActionContext ct = ActionContext.getContext();
		
		ct.put("pageInfo", pageInfo);
		ct.put("so", so);
		return "success";
	}
	
	@Action(value = "doctor_list", results = { @Result(name = "success", location = "/record/doctor_list.jsp") })
	public String doctorList() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		if (this.getSo() == null)
			so = new HospitalRecordSo();
		Long doctorId = Long.valueOf(session.get("doctorId").toString());
		if(doctorId!=null)
			so.setDoctorId(doctorId);
		else
			return "false";
		so.setCreateTime(new Date());
		
		PageInfo<HospitalRecord> pageInfo = hospitalRecordService.getPageBySo(so,pn,5);
		records = pageInfo.getList();
		
		ct.put("pageInfo", pageInfo);
		ct.put("so", so);
		return "success";
	}

	@Action(value = "detail", results = { @Result(name = "success", location = "/record/detail.jsp") })
	public String recordDetail() {
		ActionContext ct = ActionContext.getContext();
		HospitalRecord hr = hospitalRecordService.getRecordById(recordId);
		ct.put("hr", hr);

		List<HospitalPrescription> hpss = hospitalPrescriptionService.getRecordsByRecordId(recordId);
		List<HospitalMedicine> hms = new ArrayList<HospitalMedicine>();
		for (HospitalPrescription hps : hpss) {
			HospitalMedicine hm = hospitalMedicineService.getRecordById(hps.getMedicineId());
			hms.add(hm);
		}
		ct.put("hpss", hpss);
		ct.put("hms", hms);

		List<HospitalProjectCheck> hpcs = hospitalProjectCheckService.getRecordsByRecordId(recordId);
		List<String> projectNames = new ArrayList<String>();
		for (HospitalProjectCheck hpc : hpcs) {
			String hp = hospitalProjectService.getRecordById(hpc.getProjectId()).getName();
			projectNames.add(hp);
		}
		ct.put("hpcs", hpcs);
		ct.put("projectNames", projectNames);

		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "edit", results = { @Result(name = "success", location = "/record/edit.jsp") })
	public String recordEdit() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		List<HospitalPrescription> prescriptions = new ArrayList<HospitalPrescription>();
		List<HospitalMedicine> medicines = new ArrayList<HospitalMedicine>();
		List<HospitalProjectCheck> projectChecks = new ArrayList<HospitalProjectCheck>();
		List<HospitalProject> projects = new ArrayList<HospitalProject>();
		
		if(session.get("patientId")==null){
			session.put("patientId", patientId);
		}else{this.setPatientId((Long)session.get("patientId"));}
		
		if(session.get("record")==null){
			HospitalRecord hr = new HospitalRecord();
			Long doctorId = Long.valueOf(session.get("doctorId").toString());
			hr.setDoctorId(doctorId);
			this.setRecord(hr);
			session.put("record", hr);
		}else{this.setRecord((HospitalRecord)session.get("record"));}
		
		HospitalPatient patient = hospitalPatientService.getRecordById(patientId);
		
		this.setPrescriptionIds((List<Long>)session.get("prescriptionIds"));
		if(prescriptionIds!=null)
			for (Long id : prescriptionIds) {
				HospitalPrescription hp = hospitalPrescriptionService.getRecordById(id);
				prescriptions.add(hp);
				medicines.add(hospitalMedicineService.getRecordById(hp.getMedicineId()));
			}
		else{session.put("prescriptionIds",new ArrayList<Long>());}
		
		this.setProjectCheckIds((List<Long>)session.get("projectCheckIds"));
		if(projectCheckIds!=null)
			for (Long id : projectCheckIds) {
				HospitalProjectCheck hp = hospitalProjectCheckService.getRecordById(id);
				projectChecks.add(hp);
				projects.add(hospitalProjectService.getRecordById(hp.getProjectId()));
			}
		else{session.put("projectCheckIds",new ArrayList<Long>());}
			
		
		ct.put("patient", patient);
		ct.put("prescriptions", prescriptions);
		ct.put("medicines", medicines);
		ct.put("projectChecks", projectChecks);
		ct.put("projects", projects);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "add", results = { @Result(name = "order",type="redirect", location = "/order/add_hr_order",params = {"recordId","%{recordId}"}),
			@Result(name = "list",type="redirectAction", location = "list"),
			@Result(name = "success",type="json")})
	public String recordAdd() {
		try {
			recordId = hospitalRecordService.addRecord(record);
			Map<String, Object> session = ActionContext.getContext().getSession();
			this.setPrescriptionIds((List<Long>) session.get("prescriptionIds"));
			this.setProjectCheckIds((List<Long>) session.get("projectCheckIds"));
			if (this.getPrescriptionIds().size() != 0 || this.getProjectCheckIds().size() != 0) {
				for (Long id : this.getPrescriptionIds()) {
					HospitalPrescription prescription = hospitalPrescriptionService.getRecordById(id);
					prescription.setRecordId(this.getRecordId());
					hospitalPrescriptionService.updateRecordById(prescription);
				}

				for (Long id : this.getProjectCheckIds()) {
					HospitalProjectCheck projectCheck = hospitalProjectCheckService.getRecordById(id);
					projectCheck.setRecordId(this.getRecordId());
					hospitalProjectCheckService.updateRecordById(projectCheck);
				}
				result = "success";
			}else {
				result = "fail";
				message = "没有处方和检查项目添加。";
			}
		} catch (Exception ex) {
			result = "fail";
			message = ex.getMessage();
		}
		return "success";
		// return "list";
	}
	
	/**
	 * @author 全琛
	 * @time 2017年4月27日 下午4:24:52
	 */
	@Action(value = "clear_session", results = { @Result(name = "success", type="json") })
	public String clearSession() {
		try{
			ActionContext ct = ActionContext.getContext();
			Map<String, Object> session = ct.getSession();
			session.remove("record");
			session.remove("patientId");
			session.remove("prescriptionIds");
			session.remove("projectCheckIds");

			result = "success";
		}catch(Exception ex){
			result = "fail";
			message = ex.getMessage();
		}
		return "success";
	}
	
	public List<HospitalRecord> getRecords() {
		return records;
	}

	public void setRecords(List<HospitalRecord> records) {
		this.records = records;
	}

	public HospitalRecordSo getSo() {
		return so;
	}

	public void setSo(HospitalRecordSo so) {
		this.so = so;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public List<Long> getPrescriptionIds() {
		return prescriptionIds;
	}

	public void setPrescriptionIds(List<Long> prescriptionIds) {
		this.prescriptionIds = prescriptionIds;
	}

	public HospitalRecord getRecord() {
		return record;
	}

	public void setRecord(HospitalRecord record) {
		this.record = record;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
