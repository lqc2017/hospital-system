package grp3022.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grp3022.bean.HospitalAccount;
//import grp3022.bean.HospitalAccountSo;
import grp3022.dao.HospitalAccountDao;

@Service
@Transactional
public class HospitalAccountServiceImpl implements HospitalAccountService {

	
	@Autowired
    private HospitalAccountDao hospitalAccountDao;
	
	/*@Override
	public void addRecord(HospitalAccount record) {
		HospitalAccountDao.add(record);
	}

	@Override
	public void deleteRecord(Long id) {
		HospitalAccountDao.deleteByIds(id);
	}*/

	@Override
	public void updateRecordById(HospitalAccount record) {
		hospitalAccountDao.updateById(record);
	}

	
	@Override
	public HospitalAccount getRecordById(Long id) {
		return hospitalAccountDao.selectById(id);
	}


	@Override
	public Long getAIByUsername(String username) {
		HospitalAccount ha = hospitalAccountDao.selectByUserName(username);
		return ha.getAssociateId();
	}


	@Override
	public void updateByUsername(String username) {
		HospitalAccount ha = hospitalAccountDao.selectByUserName(username);
		ha.setLastLogin(new Date());
		System.out.println("当前账户："+username+","+ha.getLastLogin());
		hospitalAccountDao.updateById(ha);
	}


	/*@Override
	public PageInfo<HospitalAccount> getPageBySo(HospitalAccountSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalAccount> pageInfo = new PageInfo<HospitalAccount>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        HospitalAccountDao.selectRecordsBySo(so,pageInfo);
		
		return pageInfo;
	}*/

}
