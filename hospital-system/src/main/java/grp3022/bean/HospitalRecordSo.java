package grp3022.bean;

import java.util.Date;

public class HospitalRecordSo {

    private Date createTime;
    
    private Date createMonth;
    
    private String patientName;
    
    private Long patientId;
    
    private Long doctorId;
    
    private Short isAppraised;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(Date createMonth) {
        this.createMonth = createMonth;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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