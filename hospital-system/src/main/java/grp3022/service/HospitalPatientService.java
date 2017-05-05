package grp3022.service;



import grp3022.bean.HospitalPatient;

public interface HospitalPatientService {
	/*public void addRecord(HospitalPatient record);
	
	public void deleteRecord(Long id);*/
	
	public void updateRecordById(HospitalPatient record);
	
	public HospitalPatient getRecordById(Long id);
	
	//public PageInfo<HospitalPatient> getPageBySo(HospitalPatientSo so, Integer pageNum,Integer pageSize);
}
