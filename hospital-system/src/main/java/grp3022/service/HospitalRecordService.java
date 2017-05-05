package grp3022.service;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalRecord;
//import grp3022.bean.HospitalRecordSo;
import grp3022.bean.HospitalRecordSo;

public interface HospitalRecordService {
	public Long addRecord(HospitalRecord record);
	
	//public void deleteRecord(Long id);
	
	public void updateRecordById(HospitalRecord record);
	
	public HospitalRecord getRecordById(Long id);
	
	public HospitalRecord getRecordByOrderId(Long orderId);
	
	public PageInfo<HospitalRecord> getPageBySo(HospitalRecordSo so, Integer pageNum,Integer pageSize);
}
