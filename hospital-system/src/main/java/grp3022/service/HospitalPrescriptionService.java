package grp3022.service;

import java.util.List;

import grp3022.bean.HospitalPrescription;
//import grp3022.bean.HospitalPrescriptionSo;

public interface HospitalPrescriptionService {
	public List<Long> addRecords(List<HospitalPrescription> records);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalPrescription record);
	
	public HospitalPrescription getRecordById(Long id);
	
	public List<HospitalPrescription> getRecordsByRecordId(Long recordId);
	
	//public PageInfo<HospitalPrescription> getPageBySo(HospitalPrescriptionSo so, Integer pageNum,Integer pageSize);
}
