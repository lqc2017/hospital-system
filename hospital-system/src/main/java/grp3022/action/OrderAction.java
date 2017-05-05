package grp3022.action;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalHospitalization;
import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalOrder;
import grp3022.bean.HospitalOrderSo;
import grp3022.bean.HospitalPrescription;
import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectCheck;
import grp3022.bean.HospitalRecord;
import grp3022.service.HospitalHospitalizationService;
import grp3022.service.HospitalMedicineService;
import grp3022.service.HospitalOrderService;
import grp3022.service.HospitalPatientService;
import grp3022.service.HospitalPrescriptionService;
import grp3022.service.HospitalProjectCheckService;
import grp3022.service.HospitalProjectService;
import grp3022.service.HospitalRecordService;
@Namespace("/order")
@ParentPackage("json-default")
public class OrderAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private HospitalOrderService hospitalOrderService;
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
	@Resource
	private HospitalHospitalizationService hospitalHospitalizationService;
	@Resource
	private HospitalPatientService hospitalPatientService;

	private List<HospitalOrder> orders;
	private HospitalOrderSo so;
	private Integer pn;
	private Long orderId;
	private Long patientId;
	private short payWay; //支付方式
	private String result;
	private String message;

	@Action(value = "list", results = { @Result(name = "success", location = "/order/list.jsp") })
	public String orderList() {
		ActionContext ct = ActionContext.getContext();
		// 初始化
		if (this.getSo() == null)
			setSo(new HospitalOrderSo());
		PageInfo<HospitalOrder> pageInfo = hospitalOrderService.getPageBySo(so, pn, null,false);
		setOrders(pageInfo.getList());
		
		ct.put("so", so);
		ct.put("pageInfo", pageInfo);
		return "success";
	}
	
	@Action(value = "patient_list", results = { @Result(name = "paid_list", location = "/order/paid_list.jsp"),
			@Result(name = "unPaid_list", location = "/order/unPaid_list.jsp")})
	public String paidList(){
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		if(so==null)
			setSo(new HospitalOrderSo());
		
		if(session.get("patientId")!=null){
			Long patientId = Long.valueOf(session.get("patientId").toString());
			so.setPatientId(patientId);
			ct.put("patientId",patientId);
		}else
			return "notFound";
		
		PageInfo<HospitalOrder> pageInfo = hospitalOrderService.getPageBySo(so,pn,5,true);
			
		this.setOrders(pageInfo.getList());
		ct.put("pageInfo", pageInfo);
		ct.put("so",so);
		if(so.getStatus()==null)
			return "paid_list";
		else if(so.getStatus()==10)
			return "unPaid_list";
		else
			return "paid_list";
	}

	/**
	 * @author 全琛
	 * @time 2017年5月3日 下午2:34:13
	 */
	@Action(value = "confirm", results = {
			@Result(name = "success", type = "json") })
	public String confirmOrder() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			HospitalOrder ho = hospitalOrderService.getRecordById(orderId);
			if (session.get("cashierId") != null) {
				Long cashierId = Long.valueOf(session.get("cashierId").toString());
				ho.setCashierId(cashierId);
			} else
				return "notFound";
			ho.setStatus((short) 30);
			hospitalOrderService.updateRecordById(ho);
			
			result="success";
		} catch (Exception e) {
			result="fail";
			message=e.getMessage();
		}
		return "success";
	}

	/*@Action(value = "cancel_order", results = {
			@Result(name = "success", type = "redirectAction", location = "list", params = { "so.payer", "%{so.payer}",
					"so.status", "%{so.status}", "so.createTime", "%{so.createTime}", "so.payTime", "%{so.payTime}",
					"pn", "%{pn}" }) })
	public String cancelOrder() {
		HospitalOrder ho = hospitalOrderService.getRecordById(orderId);
		// ho.setCashierId(cashierId);
		ho.setStatus((short) 20);

		hospitalOrderService.updateRecordById(ho);
		return "success";
	}*/

	@Action(value = "pay_order", results = { @Result(name = "success", location = "/order/pay_order.jsp") })
	public String PayOrder() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		HospitalOrder ho = hospitalOrderService.getRecordById(orderId);
		ct.put("ho", ho);
		if(session.get("patientId")!=null){
			Long patientId = Long.valueOf(session.get("patientId").toString());
			ct.put("patientId",patientId);
		}else
			return "notFound";

		putMessage(ho.getType(), orderId, ct);

		return "success";
	}

	@Action(value = "pay", results = { @Result(name = "success", location = "/success.jsp") })
	public String Pay() {
		HospitalOrder ho = hospitalOrderService.getRecordById(orderId);
		ho.setPayWay(payWay);
		ho.setStatus((short)20);
		ho.setPayTime(new Date());
		ho.setPaidPrice(ho.getTotalPrice()-ho.getSubsidy());
		hospitalOrderService.updateRecordById(ho);
		System.out.println("xx");
		ActionContext ct = ActionContext.getContext();
		ct.put("patientId", ho.getPatientId());

		return "success";
	}

	@Action(value = "price_list", results = { @Result(name = "price_list", location = "/order/price_list.jsp") })
	public String priceList() {
		System.out.println(orderId);
		HospitalOrder ho = hospitalOrderService.getRecordById(orderId);
		System.out.println(ho.getId());
		ActionContext ct = ActionContext.getContext();
		ct.put("ho", ho);
		putMessage(ho.getType(), orderId, ct);
		return "price_list";
	}
	
	@Action(value="add_hr_order",results = { @Result(name = "success",type="json") })
	public String addHROrder(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Long recordId = Long.parseLong(request.getParameter("recordId"));
			HospitalRecord hr = hospitalRecordService.getRecordById(recordId);

			HospitalOrder ho = new HospitalOrder();
			double totalPrice = 0;

			List<HospitalPrescription> prescriptions = hospitalPrescriptionService.getRecordsByRecordId(recordId);
			for (HospitalPrescription ps : prescriptions) {
				totalPrice += ps.getCount() * hospitalMedicineService.getRecordById(ps.getMedicineId()).getPrice();
			}

			List<HospitalProjectCheck> projectChecks = hospitalProjectCheckService.getRecordsByRecordId(recordId);
			for (HospitalProjectCheck pc : projectChecks) {
				totalPrice += pc.getTotalCount() * hospitalProjectService.getRecordById(pc.getProjectId()).getPrice();
			}
			
			ho.setTotalPrice(totalPrice);
			ho.setType((short) 0);
			ho.setPatientId(hr.getPatientId());
			ho.setPayer(hr.getPatientName());
			ho.setSubsidy(0.00);

			Long orderId = hospitalOrderService.addRecord(ho);
			this.orderId = orderId;
			hr.setOrderId(orderId);
			hospitalRecordService.updateRecordById(hr);

			result = "success";
		} catch (Exception ex) {
			result = "fail";
			message = ex.getMessage();
		}
		
		return "success";
	}
	
	@Action(value="add_hh_order",results = { @Result(name = "success",type="json") })
	public String addHHOrder(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Long hospitalizationId = Long.parseLong(request.getParameter("hospitalizationId"));
			HospitalHospitalization hh = hospitalHospitalizationService.getRecordById(hospitalizationId);
			HospitalOrder ho = new HospitalOrder();
			ho.setTotalPrice(1500.00);
			ho.setType((short) 1);
			ho.setPatientId(hh.getPatientId());
			ho.setPayer(hh.getPatientName());
			ho.setSubsidy(0.00);
			orderId = hospitalOrderService.addRecord(ho);
			hh.setOrderId(orderId);
			hospitalHospitalizationService.updateRecordById(hh);
			
			result = "success";
		} catch (Exception e) {
			result = "fail";
			message = e.getMessage();
		}
		return "success";
	}

	void putMessage(short type, Long orderId, ActionContext ct) {
		if (type == 0) {
			Long recordId = hospitalRecordService.getRecordByOrderId(orderId).getId();
			List<HospitalPrescription> hpss = hospitalPrescriptionService.getRecordsByRecordId(recordId);
			List<HospitalMedicine> hms = new ArrayList<HospitalMedicine>();
			for (HospitalPrescription hps : hpss) {
				HospitalMedicine hm = hospitalMedicineService.getRecordById(hps.getMedicineId());
				hms.add(hm);
			}
			ct.put("hmscnt", hms.size());
			ct.put("hpss", hpss);
			ct.put("hms", hms);

			List<HospitalProjectCheck> hpcs = hospitalProjectCheckService.getRecordsByRecordId(recordId);
			List<HospitalProject> hps = new ArrayList<HospitalProject>();
			for (HospitalProjectCheck hpc : hpcs) {
				HospitalProject hp = hospitalProjectService.getRecordById(hpc.getProjectId());
				hps.add(hp);
			}
			ct.put("hpscnt", hps.size());
			ct.put("hpcs", hpcs);
			ct.put("hps", hps);
		} else {
			HospitalHospitalization hh = hospitalHospitalizationService.getRecordByOrderId(orderId);
			ct.put("time", hh.getCreateTime());
		}
	}

	public String formatCurrency(double s) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
		return currencyFormat.format(s);
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public List<HospitalOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<HospitalOrder> orders) {
		this.orders = orders;
	}

	public HospitalOrderSo getSo() {
		return so;
	}

	public void setSo(HospitalOrderSo so) {
		this.so = so;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public short getPayWay() {
		return payWay;
	}

	public void setPayWay(short payWay) {
		this.payWay = payWay;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
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
