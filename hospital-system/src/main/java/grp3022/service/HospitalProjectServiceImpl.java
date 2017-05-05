package grp3022.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectSo;
import grp3022.dao.HospitalProjectDao;

@Service
@Transactional
public class HospitalProjectServiceImpl implements HospitalProjectService {

	
	@Autowired
    private HospitalProjectDao hospitalProjectDao;
	
	/*@Override
	public void addRecord(HospitalProject record) {
		HospitalProjectDao.add(record);
	}*/

	@Override
	public void deleteRecordById(Long id) {
		hospitalProjectDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalProject record) {
		hospitalProjectDao.updateById(record);
	}

	
	@Override
	public HospitalProject getRecordById(Long id) {
		return hospitalProjectDao.selectById(id);
	}
	
	
	@Override
	public PageInfo<HospitalProject> getPageBySo(HospitalProjectSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalProject> pageInfo = new PageInfo<HospitalProject>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalProjectDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
