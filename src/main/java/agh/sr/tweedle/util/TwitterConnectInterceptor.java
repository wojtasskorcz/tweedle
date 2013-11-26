package agh.sr.tweedle.util;

import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.model.User;

/**
 * An interceptor that handles successful login to Twitter and fills
 * {@link agh.sr.tweedle.model.SessionBean} with appropriate information about
 * the established connection.
 */
@Component(value = "twitterConnectionInterceptor")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "singleton")
public class TwitterConnectInterceptor implements ConnectInterceptor<Twitter> {
	private static final Logger logger = Logger
			.getLogger(TwitterConnectInterceptor.class.getName());

	@Autowired
	private SessionBean sessionBean;

	@Override
	public void postConnect(Connection<Twitter> connection,
			WebRequest webRequest) {
		sessionBean.setTwitterProfile(connection.getApi().userOperations()
				.getUserProfile());
	}

	@Override
	public void preConnect(ConnectionFactory<Twitter> connectionFactory,
			MultiValueMap<String, String> valueMap, WebRequest webRequest) {
		sessionBean.setTwitterProfile(null);
		sessionBean.setUser(null);
	}

}
