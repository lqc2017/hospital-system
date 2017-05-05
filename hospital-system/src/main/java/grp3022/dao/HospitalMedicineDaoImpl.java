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

import grp3022.bean.HospitalMedicine;
import grp3022.bean.HospitalMedicineSo;

@Repository
public class HospitalMedicineDaoImpl implements HospitalMedicineDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		HospitalMedicine record = (HospitalMedicine)session.get(HospitalMedicine.class, id);
		session.delete(record);
	}
	
	@Override
	public void updateById(HospitalMedicine record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalMedicine selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalMedicine)session.get(HospitalMedicine.class, id);
	}


	@Override
	public void selectBySo(HospitalMedicineSo so,PageInfo<HospitalMedicine> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalMedicine.class);
		if (so.getName() != null && so.getName() != "") {
			c.add(Restrictions.like("name","%"+so.getName()+"%"));
		}
		if (so.getType() != null) {
			c.add(Restrictions.eq("type",so.getType()));
		}
		
		c.addOrder(Order.asc("name"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalMedicine> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

}
