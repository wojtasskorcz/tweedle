package agh.sr.tweedle.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

/**
 * Bean passed between models (at browser level) that keeps track of the user
 * and their session.
 */
@Component(value = "sessionBean")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class SessionBean implements Serializable {
	private TwitterProfile twitterProfile;
	private User user;

	/**
	 * Returns twitter profile of the user.
	 * 
	 * @return twitter profile of the user
	 */
	public TwitterProfile getTwitterProfile() {
		return twitterProfile;
	}

	/**
	 * Sets twitter profile of the user.
	 * 
	 * @param twitterProfile
	 *            twitter profile of the user
	 */
	public void setTwitterProfile(TwitterProfile twitterProfile) {
		this.twitterProfile = twitterProfile;
	}

	/**
	 * Returns the object representing the current user.
	 * 
	 * @return {@link agh.sr.tweedle.model.User} object representing the current
	 *         user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the object representing the current user.
	 * 
	 * @param user
	 *            {@link agh.sr.tweedle.model.User} object representing the
	 *            current user.
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
