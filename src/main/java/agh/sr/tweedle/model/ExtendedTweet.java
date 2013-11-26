package agh.sr.tweedle.model;

import org.springframework.social.twitter.api.Tweet;

/**
 * Extends Tweet class with additional field saying if the tweet should be
 * visible or hidden
 */
@SuppressWarnings("serial")
public class ExtendedTweet extends Tweet {

	private boolean hidden;

	/**
	 * Constructs object of this class based on an existing Tweet.
	 * 
	 * @param t
	 *            tweet to be extended
	 * @param hidden
	 *            boolean determining if this tweet should be visible or hidden
	 */
	public ExtendedTweet(Tweet t, boolean hidden) {
		super(t.getId(), t.getText(), t.getCreatedAt(), t.getFromUser(), t
				.getProfileImageUrl(), t.getToUserId(), t.getFromUserId(), t
				.getLanguageCode(), t.getSource());
		this.hidden = hidden;
	}

	/**
	 * Determines if this tweet is visible or hidden.
	 * 
	 * @return true if this tweet is hidden, false if it's visible
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden flag of this tweet.
	 * 
	 * @param hidden
	 *            boolean determining the visibility of this tweet
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
