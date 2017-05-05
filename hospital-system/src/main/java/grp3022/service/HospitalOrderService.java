package grp3022.service;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalOrder;
import grp3022.bean.HospitalOrderSo;

public interface HospitalOrderService {
	public Long addRecord(HospitalOrder record);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalOrder record);
	
	public HospitalOrder getRecordById(Long id);
	
	public PageInfo<HospitalOrder> getPageBySo(HospitalOrderSo so, Integer pageNum,Integer pageSize,boolean byStatus);
}
