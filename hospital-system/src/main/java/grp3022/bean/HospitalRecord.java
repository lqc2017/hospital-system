package grp3022.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "HOSPITAL_RECORD")
@DynamicUpdate
@SelectBeforeUpdate
@DynamicInsert
@SequenceGenerator(name="ID_SEQ",sequenceName="SEQUENCE_RECORD",allocationSize=1)
public class HospitalRecord {
	@Id
    @Column(name="ID",unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ID_SEQ")
    private Long id;

	@Column(name="CREATE_TIME")
    private Date createTime;

	@Column(name="SYMPTOMS",length=300)
    private String symptoms;

	@Column(name="DESCRIBE",length=200)
    private String describe;

	@Column(name="ATTENTIONS",length=200)
    private String attentions;

	@Column(name="PATIENT_ID",updatable=false)
    private Long patientId;
	
	@Column(name="DOCTOR_ID",updatable=false)
    private Long doctorId;

	@Column(name="ORDER_ID",unique=true)
    private Long orderId;

	@Column(name="PATIENT_NAME",length=20)
    private String patientName;
	
	@Column(name="IS_APPRAISED")
    private Short isAppraised;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms == null ? null : symptoms.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getAttentions() {
        return attentions;
    }

    public void setAttentions(String attentions) {
        this.attentions = attentions == null ? null : attentions.trim();
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

	public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

	public Short getIsAppraised() {
		return isAppraised;
	}

	public void setIsAppraised(Short isAppraised) {
		this.isAppraised = isAppraised;
	}
    
}