package grp3022.dao;

import com.github.pagehelper.PageInfo;


import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectSo;

public interface HospitalProjectDao{
	public void deleteById(Long id);
	
	public void updateById(HospitalProject record);
			
	public void selectBySo(HospitalProjectSo so,PageInfo<HospitalProject> pageInfo);
	
	public HospitalProject selectById(Long id);
}
