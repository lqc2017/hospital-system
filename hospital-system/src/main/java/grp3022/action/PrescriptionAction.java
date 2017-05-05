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

import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalPrescription;
import grp3022.service.HospitalMedicineService;
import grp3022.service.HospitalPrescriptionService;

@Namespace("/prescription")
@ParentPackage("json-default")
public class PrescriptionAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private HospitalPrescriptionService hospitalPrescriptionService;
	@Resource
	private HospitalMedicineService hospitalMedicineService;

	private List<Long> medicineIds;
	private List<Long> prescriptionIds;
	private List<HospitalPrescription> prescriptions;
	private HospitalPrescription prescription;
	private Long prescriptionId;
	private String result;

	@Action(value = "multi_edit", results = { @Result(name = "success", location = "/prescription/multi_edit.jsp") })
	public String multiEdit() {

		List<HospitalMedicine> medicines = new ArrayList<HospitalMedicine>();
		for (Long id : medicineIds)
			medicines.add(hospitalMedicineService.getRecordById(id));

		ActionContext ct = ActionContext.getContext();
		ct.put("medicines", medicines);
		return "success";
	}

	@Action(value = "multi_add", results = { @Result(name = "success", type = "json") })
	public String multiAdd() {
		this.setPrescriptionIds(hospitalPrescriptionService.addRecords(prescriptions));
		Map<String, Object> session = ActionContext.getContext().getSession();

		@SuppressWarnings("unchecked")
		List<Long> sessionIds = (List<Long>) session.get("prescriptionIds");
		if (sessionIds.size() == 0) {
			System.out.println("没有已添加的处方");
		}
		for (Long id : prescriptionIds) {
			sessionIds.add(id);
		}
		session.put("prescriptionIds", sessionIds);

		return "success";
	}

	@Action(value = "update", results = { @Result(name = "success", type = "json") })
	public String update() {
		hospitalPrescriptionService.updateRecordById(prescription);
		return "success";
	}

	@Action(value = "delete", results = { @Result(name = "success", type = "json") })
	public String delete() {
		try {
			hospitalPrescriptionService.deleteRecordById(prescriptionId);
			Map<String, Object> session = ActionContext.getContext().getSession();
			// 更新session
			@SuppressWarnings("unchecked")
			List<Long> hpsIds = (List<Long>) session.get("prescriptionIds");
			if (hpsIds.contains(prescriptionId)) {
				Iterator<Long> it = hpsIds.iterator();
				while (it.hasNext()) {
					Long hpcId = it.next();
					if (hpcId.longValue() == prescriptionId.longValue()) {
						it.remove();
					}
				}
			}
			session.put("prescriptionIds", hpsIds);
		} catch (Exception e) {
			this.setResult("fail");
		}
		this.setResult("success");
		return "success";
	}

	@Action(value = "edit", results = { @Result(name = "success", location = "/prescription/edit.jsp") })
	public String edit() {
		this.setPrescription(hospitalPrescriptionService.getRecordById(prescriptionId));
		System.out.println(this.getPrescription().getClass());
		HospitalMedicine medicine = hospitalMedicineService.getRecordById(prescription.getMedicineId());
		ActionContext ct = ActionContext.getContext();
		ct.put("medicine", medicine);
		return "success";
	}

	@JSON(serialize = false)
	public List<Long> getMedicineIds() {
		return medicineIds;
	}

	public void setMedicineIds(List<Long> medicineIds) {
		this.medicineIds = medicineIds;
	}

	public List<HospitalPrescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<HospitalPrescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public HospitalPrescription getPrescription() {
		return prescription;
	}

	public void setPrescription(HospitalPrescription prescription) {
		this.prescription = prescription;
	}

	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public List<Long> getPrescriptionIds() {
		return prescriptionIds;
	}

	public void setPrescriptionIds(List<Long> prescriptionIds) {
		this.prescriptionIds = prescriptionIds;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
