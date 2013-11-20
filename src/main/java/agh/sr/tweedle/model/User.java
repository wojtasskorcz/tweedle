package agh.sr.tweedle.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private String login;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ReadTweets")
	private Set<Long> readTweetIds;
	
	private Long maxTweetAgeDays;
	private Boolean showHidden;
	
	public User() {
	}
	
	public User(String login) {
		this.login = login;
		readTweetIds = new HashSet<>();
		maxTweetAgeDays = 3L;
		showHidden = false;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Set<Long> getReadTweetIds() {
		return readTweetIds;
	}

	public void setReadTweetIds(Set<Long> readTweetIds) {
		this.readTweetIds = readTweetIds;
	}

	public Long getMaxTweetAgeDays() {
		return maxTweetAgeDays;
	}

	public void setMaxTweetAgeDays(Long maxTweetAgeDays) {
		this.maxTweetAgeDays = maxTweetAgeDays;
	}

	public Boolean getShowHidden() {
		return showHidden;
	}

	public void setShowHidden(Boolean showHidden) {
		this.showHidden = showHidden;
	}
	
	@Override
	public String toString() {
		return String.format("User[login=%s,maxTweetAgeDays=%s,showHidden=%s]",
				login, maxTweetAgeDays, showHidden);
	}

}
