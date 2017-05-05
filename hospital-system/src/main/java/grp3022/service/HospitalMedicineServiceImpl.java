package grp3022.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalMedicineSo;
import grp3022.dao.HospitalMedicineDao;

@Service
@Transactional
public class HospitalMedicineServiceImpl implements HospitalMedicineService {

	
	@Autowired
    private HospitalMedicineDao hospitalMedicineDao;
	
	/*@Override
	public void addRecord(HospitalMedicine record) {
		HospitalMedicineDao.add(record);
	}*/

	@Override
	public void deleteRecordById(Long id) {
		hospitalMedicineDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalMedicine record) {
		hospitalMedicineDao.updateById(record);
	}

	
	@Override
	public HospitalMedicine getRecordById(Long id) {
		return hospitalMedicineDao.selectById(id);
	}
	
	
	@Override
	public PageInfo<HospitalMedicine> getPageBySo(HospitalMedicineSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalMedicine> pageInfo = new PageInfo<HospitalMedicine>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalMedicineDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
