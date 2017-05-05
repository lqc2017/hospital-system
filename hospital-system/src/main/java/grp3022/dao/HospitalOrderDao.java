package grp3022.dao;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalOrder;
import grp3022.bean.HospitalOrderSo;

public interface HospitalOrderDao{
	public Long insert(HospitalOrder record);
	
	public void deleteById(Long id);
	
	public void updateById(HospitalOrder record);
			
	public void selectBySo(HospitalOrderSo so,PageInfo<HospitalOrder> pageInfo);
	
	public void selectByStatus(HospitalOrderSo so,PageInfo<HospitalOrder> pageInfo);
	
	public HospitalOrder selectById(Long id);
}
