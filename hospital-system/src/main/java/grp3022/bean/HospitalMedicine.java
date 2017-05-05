package grp3022.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HOSPITAL_MEDICINE")
public class HospitalMedicine {
	@Id
    @Column(name="ID",unique=true)
    private Long id;

	@Column(name="NAME",length=20)
    private String name;

	@Column(name="TYPE")
    private Short type;
    
	@Column(name="UNIT")
    private Short unit;

	@Column(name="PRICE")
    private Double price;

	@Column(name="DESCRIBE",length=200)
    private String describe;

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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Short getUnit() {
        return unit;
    }

    public void setUnit(Short unit) {
        this.unit = unit;
    }
}