package grp3022.dao;



import grp3022.bean.HospitalPatient;

public interface HospitalPatientDao{
	public void updateById(HospitalPatient record);
			
	//public void selectBySo(HospitalPatientSo so,PageInfo<HospitalPatient> pageInfo);
	
	public HospitalPatient selectById(Long id);
}
