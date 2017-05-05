package grp3022.service;



import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalCashier;
import grp3022.bean.HospitalCashierSo;

public interface HospitalCashierService {
	//public void addRecord(HospitalCashier record);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalCashier record);
	
	public HospitalCashier getRecordById(Long id);
	
	public PageInfo<HospitalCashier> getPageBySo(HospitalCashierSo so, Integer pageNum,Integer pageSize);
}
