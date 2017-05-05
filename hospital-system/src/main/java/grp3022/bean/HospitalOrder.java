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
@Table(name = "HOSPITAL_ORDER")
@DynamicUpdate
@SelectBeforeUpdate
@DynamicInsert
@SequenceGenerator(name="ID_SEQ",sequenceName="SEQUENCE_ORDER",allocationSize=1)
public class HospitalOrder {
	@Id
    @Column(name="ID",unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ID_SEQ")  
    private Long id;

	@Column(name="PAY_WAY")
    private Short payWay;

	@Column(name="CREATE_TIME",updatable=false)
    private Date createTime;

	@Column(name="DESCRIBE",length=200)
    private String describe;

	@Column(name="STATUS")
    private Short status;

	@Column(name="TOTAL_PRICE",updatable=false)
    private Double totalPrice;

	@Column(name="PAY_TIME")
    private Date payTime;

	@Column(name="CASHIER_ID")
    private Long cashierId;

	@Column(name="PATIENT_ID",updatable=false)
    private Long patientId;

	@Column(name="SUBSIDY")
    private Double subsidy;

	@Column(name="PAID_PRICE")
    private Double paidPrice;

	@Column(name="PAYER",length=20,updatable=false)
    private String payer;

	@Column(name="TYPE",updatable=false)
    private Short type;

	@Column(name="UPDATE_TIME")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getPayWay() {
        return payWay;
    }

    public void setPayWay(Short payWay) {
        this.payWay = payWay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Double subsidy) {
        this.subsidy = subsidy;
    }

    public Double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(Double paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}