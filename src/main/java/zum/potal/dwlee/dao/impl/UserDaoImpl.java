package zum.potal.dwlee.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.vo.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	private Criteria getCriteria(){
		return getCurrentSession().createCriteria(User.class);
	}

	public UserDaoImpl() {
	}

	@Override
	public User getUser(String id){
		return (User)getCriteria()
					.add(Restrictions.eq("id", id))
					.add(Restrictions.eq("status", 'Y'))
					.uniqueResult();
	}
	
	@Override
	public User getUser(User user){
		return (User)getCriteria().add(Restrictions.eq("id",user.getId()))
				  .add(Restrictions.eq("password", user.getPassword()))
				  .add(Restrictions.eq("status", 'Y'))
				  .uniqueResult();	
	}

	@Override
	public void add(User user){
		getCurrentSession().save(user);
	}

	@Override
	public void update(User user){
		getCurrentSession().update(user);
	}

	@Override
	public void delete(User user){
		getCurrentSession().delete(user);
	}
}
