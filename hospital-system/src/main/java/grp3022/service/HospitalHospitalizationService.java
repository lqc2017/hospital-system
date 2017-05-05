package grp3022.service;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalHospitalization;
import grp3022.bean.HospitalHospitalizationSo;

public interface HospitalHospitalizationService {
	public Long addRecord(HospitalHospitalization record);
	
	public void deleteRecord(Long id);
	
	public void updateRecordById(HospitalHospitalization record);
	
	public HospitalHospitalization getRecordById(Long id);
	
	public HospitalHospitalization getRecordByOrderId(Long orderId);
	
	public PageInfo<HospitalHospitalization> getPageBySo(HospitalHospitalizationSo so, Integer pageNum,Integer pageSize);
}
