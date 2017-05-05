package grp3022.dao;



import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalCashier;
import grp3022.bean.HospitalCashierSo;

public interface HospitalCashierDao{
	//public void add(HospitalCashier record);
	
	public void deleteById(Long id);
	
	public void updateById(HospitalCashier record);
			
	public void selectBySo(HospitalCashierSo so,PageInfo<HospitalCashier> pageInfo);
	
	public HospitalCashier selectById(Long id);
}
