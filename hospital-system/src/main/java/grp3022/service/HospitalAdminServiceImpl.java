package grp3022.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalAdmin;
import grp3022.bean.HospitalAdminSo;
import grp3022.dao.HospitalAdminDao;

@Service
@Transactional
public class HospitalAdminServiceImpl implements HospitalAdminService {

	
	@Autowired
    private HospitalAdminDao hospitalAdminDao;
	
	/*@Override
	public void addRecord(HospitalAdmin record) {
		hospitalAdminDao.add(record);
	}*/

	@Override
	public void deleteRecordById(Long id) {
		hospitalAdminDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalAdmin record) {
		hospitalAdminDao.updateById(record);
	}

	
	@Override
	public HospitalAdmin getRecordById(Long id) {
		return hospitalAdminDao.selectById(id);
	}


	@Override
	public PageInfo<HospitalAdmin> getPageBySo(HospitalAdminSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalAdmin> pageInfo = new PageInfo<HospitalAdmin>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalAdminDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
