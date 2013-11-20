package agh.sr.tweedle.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agh.sr.tweedle.model.User;

@Repository
@Transactional
public class UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public User getUser(String login) {
		return (User) sessionFactory.getCurrentSession().get(User.class, login);
	}
	
	public void persistUser(User user) {
		sessionFactory.getCurrentSession().persist(user); // immediately gets attached, tested
	}
	
	public void saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}
	
	public User merge(User user) {
		return (User) sessionFactory.getCurrentSession().merge(user);
	}
	
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}
	
	public boolean isAttached(User user) {
		return sessionFactory.getCurrentSession().contains(user);
	}
	
	public String getSession() {
		return sessionFactory.getCurrentSession().toString();
	}

}
