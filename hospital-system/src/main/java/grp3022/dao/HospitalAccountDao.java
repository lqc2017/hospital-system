package grp3022.dao;



import grp3022.bean.HospitalAccount;

public interface HospitalAccountDao{
	public HospitalAccount selectByUserName(String username);
	
	public void updateById(HospitalAccount record);
			
	//public void selectBySo(HospitalAccountSo so,PageInfo<HospitalAccount> pageInfo);
	
	public HospitalAccount selectById(Long id);
}
