package grp3022.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grp3022.bean.HospitalProjectCheck;
import grp3022.dao.HospitalProjectCheckDao;

@Service
@Transactional
public class HospitalProjectCheckServiceImpl implements HospitalProjectCheckService {

	
	@Autowired
    private HospitalProjectCheckDao hospitalProjectCheckDao;
	
	

	@Override
	public void updateRecordById(HospitalProjectCheck record) {
		if(record.getDescribe().isEmpty())
			record.setDescribe("无");
		hospitalProjectCheckDao.updateById(record);
	}

	@Override
	public List<Long> addRecords(List<HospitalProjectCheck> records) {
		List<Long> ids = new ArrayList<Long>();
		for(HospitalProjectCheck record : records){
			if(record.getDescribe().isEmpty())
				record.setDescribe(null);
			ids.add(hospitalProjectCheckDao.insert(record));
		}
		return ids;
	}
	

	@Override
	public void deleteRecordById(Long id) {
		hospitalProjectCheckDao.deleteById(id);
	}
	
	@Override
	public HospitalProjectCheck getRecordById(Long id) {
		return hospitalProjectCheckDao.selectById(id);
	}


	@Override
	public List<HospitalProjectCheck> getRecordsByRecordId(Long recordId) {
		return hospitalProjectCheckDao.selectByRecordId(recordId);
	}
	
	/*@Override
	public PageInfo<HospitalProjectCheck> getPageBySo(HospitalProjectCheckSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalProjectCheck> pageInfo = new PageInfo<HospitalProjectCheck>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        HospitalProjectCheckDao.selectRecordsBySo(so,pageInfo);
		
		return pageInfo;
	}*/

}
