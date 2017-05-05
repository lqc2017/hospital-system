package grp3022.dao;

import java.util.List;

//import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalPrescription;

public interface HospitalPrescriptionDao{
	public Long insert(HospitalPrescription record);
	
	public void updateById(HospitalPrescription record);
	
	public void deleteById(Long id);
	
	public HospitalPrescription selectById(Long id);
	
	public List<HospitalPrescription> selectByRecordId(Long recordId);	
}
