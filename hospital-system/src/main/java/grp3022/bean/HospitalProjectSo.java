package grp3022.bean;

public class HospitalProjectSo {
	private String name;
	
	private String content;

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();;
	}

}
