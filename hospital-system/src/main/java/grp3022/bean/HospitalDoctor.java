package grp3022.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HOSPITAL_DOCTOR")
public class HospitalDoctor {
	@Id
	@Column(name="ID",unique=true)
    private Long id;

	@Column(name="NAME")
    private String name;

    @Column(name="AGE")
    private Short age;

    @Column(name="DEPARTMENT")
    private Short department;

    @Column(name="PRAISE")
    private Integer praise;

    @Column(name="SENIORITY")
    private Short seniority;

    @Column(name="SEX")
    private Short sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Short getDepartment() {
        return department;
    }

    public void setDepartment(Short department) {
        this.department = department;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Short getSeniority() {
        return seniority;
    }

    public void setSeniority(Short seniority) {
        this.seniority = seniority;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }
}