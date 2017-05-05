package grp3022.service;



import grp3022.bean.HospitalAccount;

public interface HospitalAccountService {
	/*public void addRecord(HospitalAccount record);
	
	public void deleteRecord(Long id);*/
	
	public void updateRecordById(HospitalAccount record);
	
	public HospitalAccount getRecordById(Long id);
	
	public Long getAIByUsername(String username);
	
	public void updateByUsername(String name);
	
	//public PageInfo<HospitalAccount> getPageBySo(HospitalPatientSo so, Integer pageNum,Integer pageSize);
}
