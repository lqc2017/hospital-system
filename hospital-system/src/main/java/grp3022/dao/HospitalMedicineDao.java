package grp3022.dao;

import com.github.pagehelper.PageInfo;


import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalMedicineSo;

public interface HospitalMedicineDao{
	public void deleteById(Long id);
	
	public void updateById(HospitalMedicine record);
			
	public void selectBySo(HospitalMedicineSo so,PageInfo<HospitalMedicine> pageInfo);
	
	public HospitalMedicine selectById(Long id);
}
