/**
 * 全琛
 * 2017年5月5日
 */
package grp3022.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalDoctor;
import grp3022.bean.HospitalDoctorSo;
import grp3022.bean.HospitalPatient;
import grp3022.service.HospitalAccountService;
import grp3022.service.HospitalDoctorService;
import grp3022.service.HospitalPatientService;
import grp3022.util.Context;

@Namespace("/doctor")
@ParentPackage("json-default")
public class DoctorAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private HospitalAccountService hospitalAccountService;
	@Resource
	private HospitalDoctorService hospitalDoctorService;
	@Resource
	private HospitalPatientService hospitalPatientService;
	
	private HospitalDoctor doctor;
	private HospitalDoctorSo so;
	private List<HospitalDoctor> doctors;
	private List<HospitalPatient> patients;
	private Short type;
	private Integer pn;
	
	@SuppressWarnings("unchecked")
	@Action(value = "home", results = { @Result(name = "success", location = "/home/doctor.jsp") })
	public String home() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		Map<String,Object> applicaion = ct.getApplication();
		
		Long doctorId = hospitalAccountService.getAIByUsername(Context.getUsername());
		hospitalAccountService.updateByUsername(Context.getUsername());
		setDoctor(hospitalDoctorService.getRecordById(doctorId));
		
		/*登陆判断*/
		if(session.get("doctorId")!=null){
			System.out.println("医生:"+this.getDoctor().getName()+"已登陆");
		}else {
			session.put("doctorId", doctorId);
			System.out.println("医生:" + this.getDoctor().getName() + "上线");

			try {
				/* 添加等待队列 */
				Map<String, Queue<Long>> patientQueue = (Map<String, Queue<Long>>) applicaion.get("patientQueue");
				patientQueue.put(this.getDoctor().getId().toString(), new LinkedList<Long>());
				System.out.println("添加 等待队列成功");
			} catch (Exception e) {
			}
		}
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "logout", results = { @Result(name = "success",type="redirect",location = "/login?logout") })
	public String logout() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		Map<String,Object> applicaion = ct.getApplication();
		
		/*清空application和session的有关内容*/
		Map<String, Queue<Long>> patientQueue = (Map<String, Queue<Long>>) applicaion.get("patientQueue");
		patientQueue.remove(session.get("doctorId").toString());
		session.remove("doctorId");
		session.remove("peekStatus");
		
		/*清空SecurityContext有关内容*/
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
	
	@SuppressWarnings({ "rawtypes" })
	@Action(value = "show_queue", results = { @Result(name = "success",type="json" )})
	public  String showQueue(){
		ActionContext ct = ActionContext.getContext();
		Queue<Long> queue = getQueue(ct);
		
		/*初始化json数据*/
		this.setPatients(new ArrayList<HospitalPatient>());
		
		/*遍历queue添加json数据*/
		Iterator it = queue.iterator();
		while(it.hasNext())
        {
            Long patientId = Long.valueOf(it.next().toString());
            System.out.println("patient id:"+patientId);
            patients.add(hospitalPatientService.getRecordById(patientId));
        }
		
		return "success";
	}
	
	@Action(value = "diagnose", results = { @Result(name = "success",location="/doctor/diagnose.jsp" ),
			@Result(name = "home",type="redirect",location="/doctor/home?empty" )})
	public  String diagnose(){
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		Queue<Long> queue = getQueue(ct);
		
		/*插入队列顶填写状态*/
		if(session.get("peekStatus")==null){
			session.put("peekStatus", "none");
		}
		
		if(queue.size()==0)
			return "home";
		else{
			Long patientId = Long.valueOf(queue.peek().toString());
			HospitalPatient patient = hospitalPatientService.getRecordById(patientId);
			ct.put("patient", patient);
			ct.put("peekStatus", session.get("peekStatus"));
			return "success";
		}
	}
	
	@Action(value="next",results={ @Result(name="success",type="redirectAction",location="diagnose")})
	public  String next(){
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ActionContext.getContext().getSession();
		
		Queue<Long> queue = getQueue(ct);
		/*清空队列顶填写状态,弹出队首*/
		session.remove("peekStatus");
		queue.poll();
		
		return "success";
	}
	
	/*更新queue头的填写状态*/
	@Action(value = "update_peek", results = { @Result(name = "success",type="json" )})
	public  String updatePeek(){
		Map<String,Object> session = ActionContext.getContext().getSession();
		
		if(type==0){
			if(session.get("peekStatus").toString().equals("none"))
				session.put("peekStatus", "hrEdited");
			else if(session.get("peekStatus").toString().equals("hhEdited"))
				session.put("peekStatus", "both");
		}
		else if(type==1){
			if(session.get("peekStatus").toString().equals("none"))
				session.put("peekStatus", "hhEdited");
			else if(session.get("peekStatus").toString().equals("hrEdited"))
				session.put("peekStatus", "both");
		}
		return "success";
	}
	
	@Action(value = "list", results = { @Result(name = "success", location = "/doctor/list.jsp") })
	public String list() {
		ActionContext ct = ActionContext.getContext();
		
		if(so==null){
			this.setSo(new HospitalDoctorSo());
		}
		
		PageInfo<HospitalDoctor> pageInfo = hospitalDoctorService.getPageBySo(so, pn, null);
		this.setDoctors(pageInfo.getList());
		
		ct.put("so", so);
		ct.put("pageInfo", pageInfo);
		return "success";
	}

	@SuppressWarnings("unchecked")
	private Queue<Long> getQueue(ActionContext ct){
		Map<String,Object> session = ct.getSession();
		Map<String,Object> applicaion = ct.getApplication();
		
		Map<String, Queue<Long>> patientQueue = (Map<String, Queue<Long>>) applicaion.get("patientQueue");
		return patientQueue.get(session.get("doctorId").toString());
	}
	public HospitalDoctor getDoctor() {
		return doctor;
	}

	public void setDoctor(HospitalDoctor doctor) {
		this.doctor = doctor;
	}

	public List<HospitalPatient> getPatients() {
		return patients;
	}

	public void setPatients(List<HospitalPatient> patients) {
		this.patients = patients;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public HospitalDoctorSo getSo() {
		return so;
	}

	public void setSo(HospitalDoctorSo so) {
		this.so = so;
	}

	public List<HospitalDoctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<HospitalDoctor> doctors) {
		this.doctors = doctors;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

}
