package grp3022.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalRecord;
import grp3022.bean.HospitalRecordSo;
//import grp3022.bean.HospitalRecordSo;
import grp3022.dao.HospitalRecordDao;

@Service
@Transactional
public class HospitalRecordServiceImpl implements HospitalRecordService {

	
	@Autowired
    private HospitalRecordDao hospitalRecordDao;
	
	@Override
	public Long addRecord(HospitalRecord record) {
		if(record.getAttentions().isEmpty())
			record.setAttentions(null);
		record.setCreateTime(new Date());
		return hospitalRecordDao.insert(record);
	}

	/*@Override
	public void deleteRecord(Long id) {
		HospitalRecordDao.deleteByIds(id);
	}*/

	@Override
	public void updateRecordById(HospitalRecord record) {
		if(record.getAttentions().isEmpty())
			record.setAttentions("无");
		hospitalRecordDao.updateById(record);
	}

	
	@Override
	public HospitalRecord getRecordById(Long id) {
		return hospitalRecordDao.selectById(id);
	}
	
	@Override
	public HospitalRecord getRecordByOrderId(Long orderId) {
		return hospitalRecordDao.selectByOrderId(orderId);
	}
	
	@Override
	public PageInfo<HospitalRecord> getPageBySo(HospitalRecordSo so, Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalRecord> pageInfo = new PageInfo<HospitalRecord>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        hospitalRecordDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}
}
