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
	
	public void persist(User user) {
		sessionFactory.getCurrentSession().persist(user); // immediately gets attached, tested
	}
	
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

}
