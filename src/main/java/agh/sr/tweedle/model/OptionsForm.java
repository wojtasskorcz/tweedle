package agh.sr.tweedle.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * POJO that backs the form in "/options" view.
 */
public class OptionsForm {

	@NotNull
	@Min(0)
	private long maxTweetAgeDays;
	private boolean showHidden;

	/**
	 * Returns the age limit of a tweet to be displayed. Tweets over this age
	 * will not be displayed.
	 * 
	 * @return age limit of a tweet
	 */
	public long getMaxTweetAgeDays() {
		return maxTweetAgeDays;
	}

	/**
	 * Sets the age limit of a tweet to be displayed. Tweets over this age will
	 * not be displayed.
	 * 
	 * @param maxTweetAgeDays
	 *            age limit of a tweet
	 */
	public void setMaxTweetAgeDays(long maxTweetAgeDays) {
		this.maxTweetAgeDays = maxTweetAgeDays;
	}

	/**
	 * Returns the flag saying if hidden tweets should be displayed anyway.
	 * 
	 * @return flag saying if hidden tweets should be displayed
	 */
	public boolean isShowHidden() {
		return showHidden;
	}

	/**
	 * Sets the flag saying if hidden tweets should be displayed anyway.
	 * 
	 * @param showHidden
	 *            flag saying if hidden tweets should be displayed
	 */
	public void setShowHidden(boolean showHidden) {
		this.showHidden = showHidden;
	}
}
