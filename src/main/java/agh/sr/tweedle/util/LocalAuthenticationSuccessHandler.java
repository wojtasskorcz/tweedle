package agh.sr.tweedle.util;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import agh.sr.tweedle.dao.UserDao;
import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.model.User;

/**
 * A handler that is invoked when user successfully logs in through Spring
 * Security. This handler overrides the default method for this event and
 * creates a {@link agh.sr.tweedle.model.SessionBean} that is bound to the user.
 */
@Component("localAuthenticationSuccessHandler")
public class LocalAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger logger = Logger
			.getLogger(LocalAuthenticationSuccessHandler.class.getName());

	@Autowired
	private SessionBean sessionBean;

	@Autowired
	private UserDao userDao;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		if (authentication == null) {
			throw new IllegalStateException(
					"Unable to get a ConnectionRepository: no user signed in");
		}
		String login = authentication.getName();
		User user = userDao.getUser(login);
		if (user == null) {
			user = new User(login);
			userDao.persist(user);
		}
		sessionBean.setUser(user);
		super.onAuthenticationSuccess(request, response, authentication);
	}

}