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

import grp3022.bean.HospitalAdmin;
import grp3022.bean.HospitalAdminSo;

@Repository
public class HospitalAdminDaoImpl implements HospitalAdminDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	

	/*@Override
	public void add(HospitalAdmin record) {
		// TODO Auto-generated method stub
	}*/

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		HospitalAdmin record = (HospitalAdmin)session.get(HospitalAdmin.class, id);
		session.delete(record);
	}
	
	@Override
	public void updateById(HospitalAdmin record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalAdmin selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalAdmin)session.get(HospitalAdmin.class, id);
	}


	@Override
	public void selectBySo(HospitalAdminSo so,PageInfo<HospitalAdmin> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalAdmin.class);
		if (so.getName() != null && so.getName() != "") {
			c.add(Restrictions.like("name","%"+so.getName()+"%"));
		}
		
		c.addOrder(Order.asc("id"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalAdmin> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

}
