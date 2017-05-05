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

import grp3022.bean.HospitalCashier;
import grp3022.bean.HospitalCashierSo;

@Repository
public class HospitalCashierDaoImpl implements HospitalCashierDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	

	/*@Override
	public void add(HospitalCashier record) {
		// TODO Auto-generated method stub
	}*/

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		HospitalCashier record = (HospitalCashier)session.get(HospitalCashier.class, id);
		session.delete(record);
	}
	
	@Override
	public void updateById(HospitalCashier record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalCashier selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalCashier)session.get(HospitalCashier.class, id);
	}


	@Override
	public void selectBySo(HospitalCashierSo so,PageInfo<HospitalCashier> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalCashier.class);
		if (so.getName() != null && so.getName() != "") {
			c.add(Restrictions.like("name","%"+so.getName()+"%"));
		}
		if (so.getSex() != null) {
			c.add(Restrictions.eq("sex",so.getSex()));
		}
		
		c.addOrder(Order.asc("id"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalCashier> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

}
