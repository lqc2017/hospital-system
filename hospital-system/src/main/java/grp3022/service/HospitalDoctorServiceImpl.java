package grp3022.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalDoctor;
import grp3022.bean.HospitalDoctorSo;
import grp3022.dao.HospitalDoctorDao;

@Service
@Transactional
public class HospitalDoctorServiceImpl implements HospitalDoctorService {

	
	@Autowired
    private HospitalDoctorDao hospitalDoctorDao;
	
	/*@Override
	public void addRecord(HospitalDoctor record) {
		HospitalDoctorDao.add(record);
	}

	@Override
	public void deleteRecord(Long id) {
		HospitalDoctorDao.deleteByIds(id);
	}*/

	@Override
	public void updateRecordById(HospitalDoctor record) {
		hospitalDoctorDao.updateById(record);
	}

	
	@Override
	public HospitalDoctor getRecordById(Long id) {
		return hospitalDoctorDao.selectById(id);
	}


	@Override
	public PageInfo<HospitalDoctor> getPageBySo(HospitalDoctorSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalDoctor> pageInfo = new PageInfo<HospitalDoctor>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalDoctorDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
