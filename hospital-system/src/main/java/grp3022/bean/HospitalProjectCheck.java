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
@Table(name = "HOSPITAL_PROJECT_CHECK")
@DynamicUpdate
@SelectBeforeUpdate
@DynamicInsert
@SequenceGenerator(name="ID_SEQ",sequenceName="SEQUENCE_PROJECT_CHECK",allocationSize=1)
public class HospitalProjectCheck {
	@Id
    @Column(name="ID",unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ID_SEQ")  
    private Long id;

	@Column(name="EXECUTE_TIME")
    private Date executeTime;

	@Column(name="IS_CHECKED")
    private Short isChecked;

	@Column(name="TOTAL_COUNT")
    private Short totalCount;

	@Column(name="DESCRIBE",length=200)
    private String describe;

	@Column(name="RECORD_ID")
    private Long recordId;

	@Column(name="PROJECT_ID",updatable=false)
    private Long projectId;

	@Column(name="CURRENT_COUNT")
    private Short currentCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Short getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Short isChecked) {
        this.isChecked = isChecked;
    }

    public Short getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Short totalCount) {
        this.totalCount = totalCount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Short getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Short currentCount) {
        this.currentCount = currentCount;
    }
}