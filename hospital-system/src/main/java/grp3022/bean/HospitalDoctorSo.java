package grp3022.bean;

public class HospitalDoctorSo {
    private String name;

    private Short department;

    private Short order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getDepartment() {
        return department;
    }

    public void setDepartment(Short department) {
        this.department = department;
    }

	public Short getOrder() {
		return order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}
}