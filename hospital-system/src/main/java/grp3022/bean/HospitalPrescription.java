package grp3022.bean;

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
@Table(name = "HOSPITAL_PRESCRIPTION")
@DynamicUpdate
@SelectBeforeUpdate
@DynamicInsert
@SequenceGenerator(name="ID_SEQ",sequenceName="SEQUENCE_PRESCRIPTION",allocationSize=1)
public class HospitalPrescription {
	@Id
    @Column(name="ID",unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ID_SEQ")  
    private Long id;

	@Column(name="TOTAL_COURSE")
    private Short totalCourse;

	@Column(name="CURRENT_COURSE")
    private Short currentCourse;

	@Column(name="INSTRUCTOR",length=200)
    private String instructor;

	@Column(name="DESCRIBE",length=200)
    private String describe;

	@Column(name="COUNT")
    private Short count;

	@Column(name="MEDICINE_ID",updatable=false)
    private Long medicineId;

	@Column(name="RECORD_ID")
    private Long recordId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(Short totalCourse) {
        this.totalCourse = totalCourse;
    }

    public Short getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Short currentCourse) {
        this.currentCourse = currentCourse;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor == null ? null : instructor.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}