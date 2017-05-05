package grp3022.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalDoctor;
import grp3022.bean.HospitalHospitalization;
import grp3022.bean.HospitalHospitalizationSo;
import grp3022.bean.HospitalOrderSo;
import grp3022.bean.HospitalPatient;
import grp3022.bean.HospitalRecord;
import grp3022.bean.HospitalRecordSo;
import grp3022.service.HospitalAccountService;
import grp3022.service.HospitalDoctorService;
import grp3022.service.HospitalHospitalizationService;
import grp3022.service.HospitalOrderService;
import grp3022.service.HospitalPatientService;
import grp3022.service.HospitalRecordService;
import grp3022.util.Context;

/**
 * @author 全琛
 *
 */
@Namespace("/patient")
@ParentPackage("json-default")
public class PatientAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private HospitalPatientService hospitalPatientService;
	@Resource
	private HospitalDoctorService hospitalDoctorService;
	@Resource
	private HospitalRecordService hospitalRecordService;
	@Resource
	private HospitalHospitalizationService hospitalHospitalizationService;
	@Resource
	private HospitalAccountService hospitalAccountService;
	@Resource
	private HospitalOrderService hospitalOrderService;
	
	private HospitalPatient patient;
	private String active;
	private Long patientId;
	private Long doctorId;
	private Short department;
	private List<HospitalDoctor> doctors;
	private String result;
	private String message;
	/*用户评价等级*/
	private Short rank;
	/*修改的病例或住院记录id*/
	private Long id;
	/*判断是住院还是病例记录的标识*/
	private Short type;
	private boolean registered;

	
	
	/**
	 * @author 全琛
	 * @time 2017年4月23日 下午3:30:45
	 */
	@Action(value = "home", results = { @Result(name = "success", location = "/home/patient.jsp") })
	public String home() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		patientId = hospitalAccountService.getAIByUsername(Context.getUsername());
		hospitalAccountService.updateByUsername(Context.getUsername());
		patient = hospitalPatientService.getRecordById(patientId);
		
		/*挂号判断*/
		if(registered){
			HospitalDoctor doctor = hospitalDoctorService.getRecordById(doctorId);
			ct.put("doctor", doctor);
		}
		
		/*登陆判断*/
		if(session.get("patientId")!=null){
			System.out.println("患者:"+patient.getName()+"已登陆");
		}
		else{
			session.put("patientId", patientId);
			System.out.println("患者:"+patient.getName()+"上线");
		}
		
		/*未评价的病例和住院次数统计*/
		long notAppraisedCnt = 0;
		HospitalHospitalizationSo hhSo = new HospitalHospitalizationSo();
		hhSo.setPatientId(patientId);
		hhSo.setIsLeave((short)1);
		hhSo.setIsAppraised((short)0);
		notAppraisedCnt += hospitalHospitalizationService.getPageBySo(hhSo, null, null).getTotal();
		
		HospitalRecordSo hrSo = new HospitalRecordSo();
		hrSo.setPatientId(patientId);
		hrSo.setIsAppraised((short)0);
		notAppraisedCnt += hospitalRecordService.getPageBySo(hrSo, null, null).getTotal();
		
		/*未缴费订单统计*/
		long unPaidCnt = 0;
		HospitalOrderSo hoSo = new HospitalOrderSo();
		hoSo.setStatus((short)10);
		hoSo.setPatientId(patientId);
		unPaidCnt = hospitalOrderService.getPageBySo(hoSo,null,null,false).getList().size();
		System.out.println("unPaidCnt:"+unPaidCnt);
		
		ct.put("unPaidCnt", unPaidCnt);
		ct.put("notAppraisedCnt", notAppraisedCnt);
		ct.put("registered", registered);
		return "success";
	}
	
	
	
	/**
	 * @author 全琛
	 * @time 2017年4月23日 上午10:00:01
	 */
	@Action(value = "information", results = { @Result(name = "success", location = "/patient/information.jsp") })
	public String message() {
		HospitalPatient patient = hospitalPatientService.getRecordById(patientId);
		ActionContext ct = ActionContext.getContext();
		if(active==null||active.trim().equals(""))
			setActive(new String("information"));
		
		ct.put("patient", patient);
		ct.put("active", active);
		return "success";
	}
	
	/**
	 * @author 全琛
	 * @time 2017年4月23日 上午10:43:27
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "register", results = { @Result(name = "success", location = "/patient/register.jsp") })
	public String register() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> application = ct.getApplication();
		
		Map<String,Queue<Long>> patientQueue = (Map<String, Queue<Long>>) application.get("patientQueue");
		List<HospitalDoctor> doctors = new ArrayList<HospitalDoctor>();
		
		if(patientQueue.size()==0){
			System.out.println("没有在线的医生");
		}
		Iterator iter = patientQueue.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			doctors.add(hospitalDoctorService.getRecordById(Long.valueOf(key.toString())));
		}
		System.out.println("在线医生人数:"+patientQueue.size());
		
		this.doctors = doctors;
		return "success";
	}
	
	/**
	 * @author 全琛
	 * @time 2017年5月3日 上午9:36:58
	 */
	@Action(value = "edit_appraise", results = { @Result(name = "success", location = "/patient/edit_appraise.jsp"),
			@Result(name = "home",type="redirectAction", location = "home")})
	public String editAppraise() {
		ActionContext ct = ActionContext.getContext();
		
		/*获得待评价的医生及病例、住院列表*/
		HospitalHospitalizationSo hhSo = new HospitalHospitalizationSo();
		hhSo.setPatientId(patientId);
		hhSo.setIsLeave((short)1);
		hhSo.setIsAppraised((short)0);
		List<HospitalHospitalization> hospitalizations =  hospitalHospitalizationService.getPageBySo(hhSo, null, null).getList();
		List<HospitalDoctor> hhDoctors = new ArrayList<HospitalDoctor>();
		for(HospitalHospitalization hh : hospitalizations){
			hhDoctors.add(hospitalDoctorService.getRecordById(hh.getDoctorId()));
		}
		
		HospitalRecordSo hrSo = new HospitalRecordSo();
		hrSo.setPatientId(patientId);
		hrSo.setIsAppraised((short)0);
		List<HospitalRecord> records = hospitalRecordService.getPageBySo(hrSo, null, null).getList();
		List<HospitalDoctor> hrDoctors = new ArrayList<HospitalDoctor>();
		for(HospitalRecord hr : records){
			hrDoctors.add(hospitalDoctorService.getRecordById(hr.getDoctorId()));
		}
		
		if(records.size()==0&&hospitalizations.size()==0)
			return "home";
		
		ct.put("hospitalizations",hospitalizations);
		ct.put("records",records);
		ct.put("hhDoctors",hhDoctors);
		ct.put("hrDoctors",hrDoctors);
		return "success";
	}
	
	/**
	 * @author 全琛
	 * @time 2017年5月3日 上午11:21:36
	 */
	@Action(value = "appraise", results = { @Result(name = "success", type="json") })
	public String appraise() {
		try {
			HospitalDoctor doctor = hospitalDoctorService.getRecordById(doctorId);
			System.out.println("医生姓名："+doctor.getName());
			/*修改评价属性*/
			doctor.setPraise(doctor.getPraise() + rank);
			
			System.out.println("医生修改后的评价分数："+doctor.getPraise());
			hospitalDoctorService.updateRecordById(doctor);
			/*修改病例或住院记录的评价状态*/
			if (type == (short) 0) {
				HospitalRecord hr = hospitalRecordService.getRecordById(id);
				hr.setIsAppraised((short) 1);
				hospitalRecordService.updateRecordById(hr);
			} else {
				HospitalHospitalization hh = hospitalHospitalizationService.getRecordById(id);
				hh.setIsAppraised((short) 1);
				hospitalHospitalizationService.updateRecordById(hh);
			} 
			
			result="success";
		} catch (Exception e) {
			result="fail";
			message=e.getMessage();
		}
		return "success";
	}
	
	/**
	 * @author 全琛
	 * @time 2017年4月23日 下午6:16:25
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "select_doctor", results = { @Result(name = "success", type = "json") })
	public String selectDoctor() {
		try{
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> application = ct.getApplication();
		
		Map<String,Queue<Long>> patientQueue = (Map<String, Queue<Long>>) application.get("patientQueue");
		List<HospitalDoctor> doctors = new ArrayList<HospitalDoctor>();
		
		System.out.println("科室值:"+department);
		if(patientQueue.size()==0){
			System.out.println("没有在线的医生");
		}
			Iterator iter = patientQueue.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				HospitalDoctor doctor = hospitalDoctorService.getRecordById(Long.valueOf(key.toString()));
				if (department != null&&doctor.getDepartment() == department) {
						doctors.add(doctor);
				} else if(department == null){
					doctors.add(doctor);
				}
			}
		
		this.doctors = doctors;
		
		result = "success";
		}catch(Exception ex){
			result = "fail";
		}
		
		return "success";
	}
	
	/**
	 * @author 全琛
	 * @time 2017年4月23日 下午7:26:30
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "add_into_queue", results = { @Result(name = "success", type = "json") })
	public String addIntoQueue() {
		try{
			ActionContext ct = ActionContext.getContext();
			Map<String,Object> application = ct.getApplication();
			
			Map<String,Queue<Long>> patientQueue = (Map<String, Queue<Long>>) application.get("patientQueue");
			patientId = hospitalAccountService.getAIByUsername(Context.getUsername());
			int length = patientQueue.get(doctorId.toString()).size();
			if(length!=0)
				message = "前面还有"+length+"位患者，预计等待时间："+(length*5)+"分钟。";
			
			patientQueue.get(doctorId.toString()).offer(patientId);
			result = "success";
		}catch(Exception ex){
			result = "fail";
		}
		return "success";
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}



	public Long getPatientId() {
		return patientId;
	}



	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}



	public List<HospitalDoctor> getDoctors() {
		return doctors;
	}



	public void setDoctors(List<HospitalDoctor> doctors) {
		this.doctors = doctors;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}



	public Long getDoctorId() {
		return doctorId;
	}



	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public boolean isRegistered() {
		return registered;
	}



	public void setRegistered(boolean registered) {
		this.registered = registered;
	}



	public Short getDepartment() {
		return department;
	}



	public void setDepartment(Short department) {
		this.department = department;
	}



	public Short getRank() {
		return rank;
	}



	public void setRank(Short rank) {
		this.rank = rank;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Short getType() {
		return type;
	}



	public void setType(Short type) {
		this.type = type;
	}



	public HospitalPatient getPatient() {
		return patient;
	}



	public void setPatient(HospitalPatient patient) {
		this.patient = patient;
	}
	
	
}
