package grp3022.service;



import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalAdmin;
import grp3022.bean.HospitalAdminSo;

public interface HospitalAdminService {
	//public void addRecord(HospitalAdmin record);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalAdmin record);
	
	public HospitalAdmin getRecordById(Long id);
	
	public PageInfo<HospitalAdmin> getPageBySo(HospitalAdminSo so, Integer pageNum,Integer pageSize);
}
