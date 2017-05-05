package grp3022.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalHospitalization;
import grp3022.bean.HospitalHospitalizationSo;
//import grp3022.bean.HospitalHospitalizationSo;
import grp3022.dao.HospitalHospitalizationDao;

@Service
@Transactional
public class HospitalHospitalizationServiceImpl implements HospitalHospitalizationService {

	
	@Autowired
    private HospitalHospitalizationDao hospitalHospitalizationDao;
	
	@Override
	public Long addRecord(HospitalHospitalization record) {
		if(record.getDescribe().isEmpty())
			record.setDescribe(null);
		record.setCreateTime(new Date());
		return hospitalHospitalizationDao.insert(record);
	}

	@Override
	public void deleteRecord(Long id) {
		hospitalHospitalizationDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalHospitalization record) {
		hospitalHospitalizationDao.updateById(record);
	}

	
	@Override
	public HospitalHospitalization getRecordById(Long id) {
		return hospitalHospitalizationDao.selectById(id);
	}


	@Override
	public HospitalHospitalization getRecordByOrderId(Long orderId) {
		return hospitalHospitalizationDao.selectByOrderId(orderId);
	}
	
	@Override
	public PageInfo<HospitalHospitalization> getPageBySo(HospitalHospitalizationSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalHospitalization> pageInfo = new PageInfo<HospitalHospitalization>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalHospitalizationDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
