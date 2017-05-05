package grp3022.service;



import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalDoctor;
import grp3022.bean.HospitalDoctorSo;

public interface HospitalDoctorService {
	/*public void addRecord(HospitalDoctor record);
	
	public void deleteRecord(Long id);*/
	
	public void updateRecordById(HospitalDoctor record);
	
	public HospitalDoctor getRecordById(Long id);
	
	public PageInfo<HospitalDoctor> getPageBySo(HospitalDoctorSo so, Integer pageNum,Integer pageSize);
}
