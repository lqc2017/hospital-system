package grp3022.dao;

import com.github.pagehelper.PageInfo;

//import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalRecord;
//import grp3022.bean.HospitalRecordSo;
import grp3022.bean.HospitalRecordSo;

public interface HospitalRecordDao{
	public Long insert(HospitalRecord record);
	
	public void updateById(HospitalRecord record);
			
	public void selectBySo(HospitalRecordSo so,PageInfo<HospitalRecord> pageInfo);
	
	public HospitalRecord selectById(Long id);
	
	public HospitalRecord selectByOrderId(Long orderId);
}
