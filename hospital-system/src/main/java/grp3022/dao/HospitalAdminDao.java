package grp3022.dao;


import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalAdmin;
import grp3022.bean.HospitalAdminSo;

public interface HospitalAdminDao{
	//public void add(HospitalAdmin record);
	
	public void deleteById(Long id);
	
	public void updateById(HospitalAdmin record);
			
	public void selectBySo(HospitalAdminSo so,PageInfo<HospitalAdmin> pageInfo);
	
	public HospitalAdmin selectById(Long id);
}
