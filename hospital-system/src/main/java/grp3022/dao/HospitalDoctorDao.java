package grp3022.dao;



import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalDoctor;
import grp3022.bean.HospitalDoctorSo;

public interface HospitalDoctorDao{
	public void updateById(HospitalDoctor record);
			
	public void selectBySo(HospitalDoctorSo so,PageInfo<HospitalDoctor> pageInfo);
	
	public HospitalDoctor selectById(Long id);
}
