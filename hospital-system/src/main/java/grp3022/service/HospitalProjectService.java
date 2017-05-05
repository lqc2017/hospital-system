package grp3022.service;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectSo;

public interface HospitalProjectService {
	//public void addRecord(HospitalProject record);
	
	public void deleteRecordById(Long id);
	
	public void updateRecordById(HospitalProject record);
	
	public HospitalProject getRecordById(Long id);
	
	public PageInfo<HospitalProject> getPageBySo(HospitalProjectSo so, Integer pageNum,Integer pageSize);
}
