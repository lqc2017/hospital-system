package grp3022.dao;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;

import grp3022.bean.HospitalDoctor;
import grp3022.bean.HospitalDoctorSo;

@Repository
public class HospitalDoctorDaoImpl implements HospitalDoctorDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void updateById(HospitalDoctor record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalDoctor selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalDoctor)session.get(HospitalDoctor.class, id);
	}


	@Override
	public void selectBySo(HospitalDoctorSo so,PageInfo<HospitalDoctor> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalDoctor.class);
		if (so.getName() != null && so.getName() != "") {
			c.add(Restrictions.like("name","%"+so.getName()+"%"));
		}
		if (so.getDepartment() != null) {
			c.add(Restrictions.eq("department",so.getDepartment()));
		}
		if(so.getOrder()==null || so.getOrder()==0)
			c.addOrder(Order.desc("name"));
		else if(so.getOrder() == 1){
			c.addOrder(Order.desc("seniority"));
		}else{
			c.addOrder(Order.desc("praise"));
		}
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalDoctor> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

}
