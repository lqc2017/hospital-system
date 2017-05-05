package grp3022.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalRecord;
import grp3022.bean.HospitalRecordSo;

@Repository
public class HospitalRecordDaoImpl implements HospitalRecordDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Long insert(HospitalRecord record) {
		Session session = sessionFactory.getCurrentSession();
		session.save(record);
		return record.getId();
	}
	
	@Override
	public void updateById(HospitalRecord record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalRecord selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalRecord)session.get(HospitalRecord.class, id);
	}

	@Override
	public HospitalRecord selectByOrderId(Long orderId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(HospitalRecord.class).add(Restrictions.eq("orderId",orderId));
		//一对一关系
		return c.list().size()!=1?null:(HospitalRecord) c.list().get(0);
	}

	@Override
	public void selectBySo(HospitalRecordSo so,PageInfo<HospitalRecord> pageInfo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(HospitalRecord.class);
		if (so.getPatientName() != null && so.getPatientName() != "") {
			c.add(Restrictions.like("patientName","%"+so.getPatientName()+"%"));
		}
		if (so.getCreateTime() != null) {
			c.add(Restrictions.sqlRestriction("to_char(CREATE_TIME,'YYYY-MM-DD') = to_char(?,'YYYY-MM-DD')",so.getCreateTime(),DateType.INSTANCE));
		}
		if (so.getCreateMonth() != null) {
			c.add(Restrictions.sqlRestriction("to_char(CREATE_TIME,'YYYY-MM') = to_char(?,'YYYY-MM')",so.getCreateMonth(),DateType.INSTANCE));
		}
		if (so.getPatientId() != null) {
			c.add(Restrictions.eq("patientId",so.getPatientId()));
		}
		if (so.getDoctorId() != null) {
			c.add(Restrictions.eq("doctorId",so.getDoctorId()));
		}
		if (so.getIsAppraised() != null) {
			c.add(Restrictions.eq("isAppraised",so.getIsAppraised()));
		}
		c.addOrder(Order.desc("createTime"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalRecord> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

}
