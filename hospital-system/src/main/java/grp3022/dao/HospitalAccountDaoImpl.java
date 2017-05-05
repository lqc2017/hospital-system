package grp3022.dao;


import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import grp3022.bean.HospitalAccount;

@Repository
@Transactional
public class HospitalAccountDaoImpl implements HospitalAccountDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void updateById(HospitalAccount record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalAccount selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalAccount)session.get(HospitalAccount.class, id);
	}

	@Override
	public HospitalAccount selectByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(HospitalAccount.class).add(Restrictions.eq("username",username));
		//一对一关系
		return c.list().size()!=1?null:(HospitalAccount) c.list().get(0);
	}


	/*@Override
	public void selectBySo(HospitalAccountSo so,PageInfo<HospitalAccount> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalAccount.class);
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
		List<HospitalAccount> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}
*/
}
