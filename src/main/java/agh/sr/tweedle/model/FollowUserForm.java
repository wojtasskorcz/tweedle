package agh.sr.tweedle.model;

/**
 * POJO that backs the form in "/followed" view.
 */
public class FollowUserForm {
	private String screenName;

	/**
	 * Returns screen name (on Twitter) of the person to be followed.
	 * 
	 * @return screen name of the person to be followed
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * Sets the screen name of the person to be followed.
	 * 
	 * @param screenName
	 *            screen name of the person to be followed
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
