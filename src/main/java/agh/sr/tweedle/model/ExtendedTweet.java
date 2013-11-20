package agh.sr.tweedle.model;

import org.springframework.social.twitter.api.Tweet;

@SuppressWarnings("serial")
public class ExtendedTweet extends Tweet {
	
	private boolean hidden;
	
	public ExtendedTweet(Tweet t, boolean hidden) {
		super(t.getId(), t.getText(), t.getCreatedAt(), t.getFromUser(), t.getProfileImageUrl(), t.getToUserId(),
				t.getFromUserId(), t.getLanguageCode(), t.getSource());
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
