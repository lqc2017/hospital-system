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

import grp3022.bean.HospitalProject;
import grp3022.bean.HospitalProjectSo;

@Repository
public class HospitalProjectDaoImpl implements HospitalProjectDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		HospitalProject record = (HospitalProject)session.get(HospitalProject.class, id);
		session.delete(record);
	}
	
	@Override
	public void updateById(HospitalProject record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}
	
	@Override
	public HospitalProject selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (HospitalProject)session.get(HospitalProject.class, id);
	}


	@Override
	public void selectBySo(HospitalProjectSo so,PageInfo<HospitalProject> pageInfo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria c = session.createCriteria(HospitalProject.class);
		if (so.getName() != null && so.getName() != "") {
			c.add(Restrictions.like("name","%"+so.getName()+"%"));
		}
		if (so.getContent() != null && so.getContent() != "") {
			c.add(Restrictions.like("content","%"+so.getContent()+"%"));
		}
		
		c.addOrder(Order.asc("name"));
		int start =  (pageInfo.getPageNum()-1)*pageInfo.getPageSize();
		pageInfo.setTotal(c.list().size());
		double buffer = (double)pageInfo.getTotal()/pageInfo.getPageSize();
		pageInfo.setPages((int)Math.ceil(buffer));
		
		c.setFirstResult(start);
		c.setMaxResults(pageInfo.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<HospitalProject> records = c.list();
		pageInfo.setList(records);
		pageInfo.setSize(records.size());
	}

}
