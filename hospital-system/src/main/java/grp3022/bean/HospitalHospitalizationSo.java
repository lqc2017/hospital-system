package grp3022.bean;

import java.util.Date;

public class HospitalHospitalizationSo {
	private Short order;
	
    private Short isLeave;

    private Long patientId;
    
    private Long doctorId;

    private Date createTime;
    
    private Date createMonth;

    private Date leaveTime;

    private String patientName;
    
    private Short isAppraised;
    
    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
        this.order = order;
    }
    
    public Short getIsLeave() {
        return isLeave;
    }

    public void setIsLeave(Short isLeave) {
        this.isLeave = isLeave;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Date getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(Date createMonth) {
        this.createMonth = createMonth;
    }
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	
	public Short getIsAppraised() {
		return isAppraised;
	}

	public void setIsAppraised(Short isAppraised) {
		this.isAppraised = isAppraised;
	}	
}