package grp3022.service;

import java.util.List;


import grp3022.bean.HospitalProjectCheck;

public interface HospitalProjectCheckService {
	public List<Long> addRecords(List<HospitalProjectCheck> records);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalProjectCheck record);
	
	public HospitalProjectCheck getRecordById(Long id);
	
	public List<HospitalProjectCheck> getRecordsByRecordId(Long recordId);
	
	//public PageInfo<HospitalProjectCheck> getPageBySo(HospitalProjectCheckSo so, Integer pageNum,Integer pageSize);
}
