package grp3022.service;

import com.github.pagehelper.PageInfo;


import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalMedicineSo;

public interface HospitalMedicineService {
	//public void addRecord(HospitalMedicine record);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalMedicine record);
	
	public HospitalMedicine getRecordById(Long id);
	
	public PageInfo<HospitalMedicine> getPageBySo(HospitalMedicineSo so, Integer pageNum,Integer pageSize);
}
