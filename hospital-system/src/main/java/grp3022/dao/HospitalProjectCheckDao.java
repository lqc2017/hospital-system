package grp3022.dao;

import java.util.List;

import grp3022.bean.HospitalProjectCheck;

public interface HospitalProjectCheckDao{
	public Long insert(HospitalProjectCheck record);
	
	public void updateById(HospitalProjectCheck record);
	
	public void deleteById(Long id);
			
	//public void selectBySo(HospitalProjectCheckSo so,PageInfo<HospitalProjectCheck> pageInfo);
	
	public HospitalProjectCheck selectById(Long id);
	
	public List<HospitalProjectCheck> selectByRecordId(Long recordId);	
}
