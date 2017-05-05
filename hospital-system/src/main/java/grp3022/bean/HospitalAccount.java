package grp3022.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "HOSPITAL_ACCOUNT")
public class HospitalAccount implements Serializable{
	@Column(name="USERNAME",length=20,unique=true)
    private String username;

    @Column(name="PASSWORD",length=20)
    private String password;

    @Id
    @Column(name="ROLE")
    private Short role;

    @Id
    @Column(name="ASSOCIATE_ID")
    private Long associateId;
    
    @Column(name="LAST_LOGIN")
    private Date lastLogin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public Long getAssociateId() {
        return associateId;
    }

    public void setAssociateId(Long associateId) {
        this.associateId = associateId;
    }

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}