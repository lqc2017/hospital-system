package grp3022.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalOrder;
import grp3022.bean.HospitalOrderSo;
import grp3022.dao.HospitalOrderDao;

@Service
@Transactional
public class HospitalOrderServiceImpl implements HospitalOrderService {

	
	@Autowired
    private HospitalOrderDao hospitalOrderDao;
	
	@Override
	public Long addRecord(HospitalOrder record) {
		record.setCreateTime(new Date());
		return hospitalOrderDao.insert(record);
	}

	@Override
	public void deleteRecordById(Long id) {
		hospitalOrderDao.deleteById(id);
	}

	@Override
	public void updateRecordById(HospitalOrder record) {
		record.setUpdateTime(new Date());
		hospitalOrderDao.updateById(record);
	}

	
	@Override
	public HospitalOrder getRecordById(Long id) {
		return hospitalOrderDao.selectById(id);
	}
	
	@Override
	public PageInfo<HospitalOrder> getPageBySo(HospitalOrderSo so, Integer pageNum,Integer pageSize,boolean byStatus) {
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
        
        PageInfo<HospitalOrder> pageInfo = new PageInfo<HospitalOrder>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        if(byStatus)
        	hospitalOrderDao.selectByStatus(so,pageInfo);
        else
        	hospitalOrderDao.selectBySo(so,pageInfo);
		
		return pageInfo;
	}

}
