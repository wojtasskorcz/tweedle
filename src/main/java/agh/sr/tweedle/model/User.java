package agh.sr.tweedle.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 666L;

	@Id
	private String login;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ReadTweets")
	private Set<Long> hiddenTweetIds;

	private Long maxTweetAgeDays;
	private Boolean showHidden;

	/**
	 * Default constructor used by Hibernate to create this bean.
	 */
	public User() {
	}

	/**
	 * Constructor invoked programatically to create a User and set all the
	 * fields to their default values.
	 * 
	 * @param login
	 *            login of the user to be created
	 */
	public User(String login) {
		this.login = login;
		hiddenTweetIds = new HashSet<>();
		maxTweetAgeDays = 3L;
		showHidden = false;
	}

	/**
	 * Returns the login of this user.
	 * 
	 * @return login of this user
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login of this user.
	 * 
	 * @param login
	 *            new login for this user
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the set of ID's of hidden tweets for this user.
	 * 
	 * @return set of ID's of hidden tweets for this user
	 */
	public Set<Long> getHiddenTweetIds() {
		return hiddenTweetIds;
	}

	/**
	 * Sets the set of ID's of hidden tweets for this user.
	 * 
	 * @param hiddenTweetIds
	 *            new set of ID's of hidden tweets for this user
	 */
	public void setHiddenTweetIds(Set<Long> hiddenTweetIds) {
		this.hiddenTweetIds = hiddenTweetIds;
	}

	/**
	 * Retuns the age limit of tweets displayed to this user.
	 * 
	 * @return age limit of tweets displayed to this user
	 */
	public Long getMaxTweetAgeDays() {
		return maxTweetAgeDays;
	}

	/**
	 * Sets the age limit of tweets displayed to this user.
	 * 
	 * @param maxTweetAgeDays
	 *            age limit of tweets displayed to this user
	 */
	public void setMaxTweetAgeDays(Long maxTweetAgeDays) {
		this.maxTweetAgeDays = maxTweetAgeDays;
	}

	/**
	 * Returns the flag saying if this user should be shown hidden tweets.
	 * 
	 * @return flag saying if this user should be shown hidden tweets
	 */
	public Boolean getShowHidden() {
		return showHidden;
	}

	/**
	 * Sets the flag saying if this user should be shown hidden tweets.
	 * 
	 * @param showHidden
	 *            flag saying if this user should be shown hidden tweets
	 */
	public void setShowHidden(Boolean showHidden) {
		this.showHidden = showHidden;
	}

	@Override
	public String toString() {
		return String.format("User[login=%s,maxTweetAgeDays=%s,showHidden=%s]",
				login, maxTweetAgeDays, showHidden);
	}

}
