package grp3022.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalCashier;
import grp3022.bean.HospitalCashierSo;
import grp3022.dao.HospitalCashierDao;

@Service
@Transactional
public class HospitalCashierServiceImpl implements HospitalCashierService {

	
	@Autowired
    private HospitalCashierDao hospitalCashierDao;
	
	/*@Override
	public void addRecord(HospitalCashier record) {
		hospitalCashierDao.add(record);
	}*/

	@Override
	public void deleteRecordById(Long id) {
		hospitalCashierDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalCashier record) {
		hospitalCashierDao.updateById(record);
	}

	
	@Override
	public HospitalCashier getRecordById(Long id) {
		return hospitalCashierDao.selectById(id);
	}


	@Override
	public PageInfo<HospitalCashier> getPageBySo(HospitalCashierSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalCashier> pageInfo = new PageInfo<HospitalCashier>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalCashierDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
