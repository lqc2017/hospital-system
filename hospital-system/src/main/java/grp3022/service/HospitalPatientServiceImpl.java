package grp3022.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grp3022.bean.HospitalPatient;
//import grp3022.bean.HospitalPatientSo;
import grp3022.dao.HospitalPatientDao;

@Service
@Transactional
public class HospitalPatientServiceImpl implements HospitalPatientService {

	
	@Autowired
    private HospitalPatientDao hospitalPatientDao;
	
	/*@Override
	public void addRecord(HospitalPatient record) {
		HospitalPatientDao.add(record);
	}

	@Override
	public void deleteRecord(Long id) {
		HospitalPatientDao.deleteByIds(id);
	}*/

	@Override
	public void updateRecordById(HospitalPatient record) {
		hospitalPatientDao.updateById(record);
	}

	
	@Override
	public HospitalPatient getRecordById(Long id) {
		return hospitalPatientDao.selectById(id);
	}


	/*@Override
	public PageInfo<HospitalPatient> getPageBySo(HospitalPatientSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalPatient> pageInfo = new PageInfo<HospitalPatient>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        HospitalPatientDao.selectRecordsBySo(so,pageInfo);
		
		return pageInfo;
	}*/

}
