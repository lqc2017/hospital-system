package grp3022.dao;

import com.github.pagehelper.PageInfo;


import grp3022.bean.HospitalHospitalization;
import grp3022.bean.HospitalHospitalizationSo;

public interface HospitalHospitalizationDao{
	public Long insert(HospitalHospitalization record);
	
	public void deleteById(Long id);
	
	public void updateById(HospitalHospitalization record);
			
	public void selectBySo(HospitalHospitalizationSo so,PageInfo<HospitalHospitalization> pageInfo);
	
	public HospitalHospitalization selectById(Long id);
	
	public HospitalHospitalization selectByOrderId(Long orderId);
}
