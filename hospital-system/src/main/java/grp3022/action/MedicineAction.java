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

import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalMedicineSo;
import grp3022.bean.HospitalRecord;
import grp3022.service.HospitalMedicineService;
import grp3022.service.HospitalPrescriptionService;

@Namespace("/medicine")
@ParentPackage("json-default")
public class MedicineAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private HospitalMedicineService hospitalMedicineService;
	@Resource
	private HospitalPrescriptionService hospitalPrescriptionService;

	private PageInfo<HospitalMedicine> pageInfo;
	private HospitalRecord record;
	private HospitalMedicineSo so;
	private HospitalMedicine medicine;
	private Long medicineId;
	private String result;
	private Integer pn;

	@Action(value = "list", results = { @Result(name = "success", location = "/medicine/list.jsp") })
	public String list() {
		if (this.getSo() == null)
			this.setSo(new HospitalMedicineSo());
		ActionContext ct = ActionContext.getContext();
		PageInfo<HospitalMedicine> pageInfo = hospitalMedicineService.getPageBySo(so, pn, 10);
		
		this.setPageInfo(pageInfo);
		ct.put("so", so);
		return "success";
	}
	
	@Action(value = "edit", results = { @Result(name = "success", location = "/medicine/edit.jsp") })
	public String edit() {
		System.out.println(medicineId);
		medicine = hospitalMedicineService.getRecordById(medicineId);
		return "success";
	}
	
	@Action(value = "update", results = { @Result(name = "success", type = "json") })
	public String update() {
		try{
			hospitalMedicineService.updateRecordById(medicine);
			setResult("success");
		}catch(Exception e){
			setResult("fail");
		}
		return "success";
	}
	
	@Action(value = "delete", results = { @Result(name = "success",type="json")})
	public String delete() {
		try{
			hospitalMedicineService.deleteRecordById(medicineId);
			result = "success";
		}catch(Exception e){
			result = "fail";
		}
		return "success";
	}
	
	@Action(value = "select", results = { @Result(name = "success", location = "/medicine/select.jsp") })
	public String select() {
		// 默认显示口服药品
		if (this.getSo() == null)
			this.setSo(new HospitalMedicineSo());
		if (so.getType() == null)
			so.setType((short) 10);

		PageInfo<HospitalMedicine> pageInfo = hospitalMedicineService.getPageBySo(so, pn, 8);
		List<Long> selectedMedicineIds = new ArrayList<Long>();

		ActionContext ct = ActionContext.getContext();
		Map<String, Object> session = ct.getSession();
		session.put("record", this.getRecord());
		System.out.println(record.getPatientName());
		System.out.println(record.getSymptoms());
		
		@SuppressWarnings("unchecked")
		List<Long> prescriptionIds = (List<Long>) session.get("prescriptionIds");
		for (Long id : prescriptionIds) {
			selectedMedicineIds.add(hospitalPrescriptionService.getRecordById(id).getMedicineId());
		}
		this.setPageInfo(pageInfo);
		ct.put("smi", selectedMedicineIds);
		ct.put("so", so);

		return "success";
	}
	
	@Action(value = "change_table", results = { @Result(name = "success", type = "json") })
	public String changeTable() {
		PageInfo<HospitalMedicine> pageInfo = hospitalMedicineService.getPageBySo(so, pn, 8);

		this.setPageInfo(pageInfo);
		return "success";
	}
	
	public String formatCurrency(double s) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
		return currencyFormat.format(s);
	}

	public HospitalMedicineSo getSo() {
		return so;
	}

	public void setSo(HospitalMedicineSo so) {
		this.so = so;
	}

	public PageInfo<HospitalMedicine> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<HospitalMedicine> pageInfo) {
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

	public HospitalMedicine getMedicine() {
		return medicine;
	}

	public void setMedicine(HospitalMedicine medicine) {
		this.medicine = medicine;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
