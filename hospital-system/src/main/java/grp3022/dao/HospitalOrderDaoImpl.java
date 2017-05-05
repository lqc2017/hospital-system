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

import grp3022.bean.HospitalOrder;
import grp3022.bean.HospitalOrderSo;
import grp3022.bean.HospitalPrescription;

@Repository
public class HospitalOrderDaoImpl implements HospitalOrderDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public Long insert(HospitalOrder record) {
		Session session = sessionFactory.getCurrentSession();
		session.save(record);
		return record.getId();
	}
	
	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		HospitalOrder record = (HospitalOrder)session.get(HospitalPrescription.class, id);
        session.delete(record);
	}
	
	@Override
	public void updateById(HospitalOrder record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalOrder selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalOrder)session.get(HospitalOrder.class, id);
	}

	@Override
	public void selectBySo(HospitalOrderSo so,PageInfo<HospitalOrder> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalOrder.class);
		if (so.getPayer() != null && so.getPayer() != "") {
			c.add(Restrictions.like("payer","%"+so.getPayer()+"%"));
		}
		if (so.getStatus() != null) {
			c.add(Restrictions.eq("status",so.getStatus()));
		}
		if (so.getCreateTime() != null) {
			c.add(Restrictions.sqlRestriction("to_char(CREATE_TIME,'YYYY-MM-DD') = to_char(?,'YYYY-MM-DD')",so.getCreateTime(),DateType.INSTANCE));
		}
		if (so.getPayTime() != null) {
			c.add(Restrictions.sqlRestriction("to_char(PAY_TIME,'YYYY-MM-DD') = to_char(?,'YYYY-MM-DD')",so.getPayTime(),DateType.INSTANCE));
		}
		if (so.getPatientId() != null) {
			c.add(Restrictions.eq("patientId",so.getPatientId()));
		}
		c.addOrder(Order.desc("createTime"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalOrder> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

	@Override
	public void selectByStatus(HospitalOrderSo so, PageInfo<HospitalOrder> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalOrder.class);
		if (so.getStatus() == null) {
			c.add(Restrictions.between("status",(short)20,(short)30));
		}
		else
			c.add(Restrictions.eq("status",so.getStatus()));
		
		if (so.getPatientId() != null) {
			c.add(Restrictions.eq("patientId",so.getPatientId()));
		}
		c.addOrder(Order.desc("createTime"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalOrder> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}
	
}
