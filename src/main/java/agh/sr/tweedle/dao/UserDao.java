package agh.sr.tweedle.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agh.sr.tweedle.model.User;

/**
 * Data Access Object that operates on {@link agh.sr.tweedle.model.User}
 * objects.
 */
@Repository
@Transactional
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Retrieves user from the database.
	 * 
	 * @param login
	 *            login of the user to be retrieved
	 * @return appropriate user
	 */
	public User getUser(String login) {
		return (User) sessionFactory.getCurrentSession().get(User.class, login);
	}

	/**
	 * Persists user to the database.
	 * 
	 * @param user
	 *            User to be persisted
	 */
	public void persist(User user) {
		// immediately gets attached, tested
		sessionFactory.getCurrentSession().persist(user);
	}

	/**
	 * Updates already existing user in the database.
	 * 
	 * @param user
	 *            user with current state that should be copied to the database
	 */
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

}
