package grp3022.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grp3022.bean.HospitalPrescription;
//import grp3022.bean.HospitalPrescriptionSo;
import grp3022.dao.HospitalPrescriptionDao;

@Service
@Transactional
public class HospitalPrescriptionServiceImpl implements HospitalPrescriptionService {

	
	@Autowired
    private HospitalPrescriptionDao hospitalPrescriptionDao;
	
	@Override
	public List<Long> addRecords(List<HospitalPrescription> records) {
		List<Long> ids = new ArrayList<Long>();
		for(HospitalPrescription record : records){
			ids.add(hospitalPrescriptionDao.insert(record));
		}
		return ids;
	}

	@Override
	public void deleteRecordById(Long id) {
		hospitalPrescriptionDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalPrescription record) {
		hospitalPrescriptionDao.updateById(record);
	}

	
	@Override
	public HospitalPrescription getRecordById(Long id) {
		return hospitalPrescriptionDao.selectById(id);
	}


	@Override
	public List<HospitalPrescription> getRecordsByRecordId(Long recordId) {
		return hospitalPrescriptionDao.selectByRecordId(recordId);
	}
	
	/*@Override
	public PageInfo<HospitalPrescription> getPageBySo(HospitalPrescriptionSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalPrescription> pageInfo = new PageInfo<HospitalPrescription>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        HospitalPrescriptionDao.selectRecordsBySo(so,pageInfo);
		
		return pageInfo;
	}*/

}
