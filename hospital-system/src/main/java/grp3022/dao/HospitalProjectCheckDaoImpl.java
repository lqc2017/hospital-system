package grp3022.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import grp3022.bean.HospitalProjectCheck;

@Repository
public class HospitalProjectCheckDaoImpl implements HospitalProjectCheckDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Long insert(HospitalProjectCheck record) {
		Session session = sessionFactory.getCurrentSession();
		session.save(record);
		return record.getId();
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		HospitalProjectCheck record = (HospitalProjectCheck)session.get(HospitalProjectCheck.class, id);
        session.delete(record);
	}
	
	@Override
	public void updateById(HospitalProjectCheck record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalProjectCheck selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalProjectCheck)session.get(HospitalProjectCheck.class, id);
	}

	@Override
	public List<HospitalProjectCheck> selectByRecordId(Long recordId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(HospitalProjectCheck.class).add(Restrictions.eq("recordId",recordId));
		@SuppressWarnings("unchecked")
		List<HospitalProjectCheck> records = c.list();
		return records;
	}

	/*@Override
	public void selectBySo(HospitalProjectCheckSo so,PageInfo<HospitalProjectCheck> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalProjectCheck.class);
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
		System.out.println("总记录数："+pageInfo.getTotal());
		System.out.println("每页容量："+pageInfo.getPageSize());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		System.out.println("总页数："+pageInfo.getPages());
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalProjectCheck> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}
*/
}
