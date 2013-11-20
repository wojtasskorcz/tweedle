package agh.sr.tweedle.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OptionsForm {
	
	@NotNull  
	@Min(0)
	private long maxTweetAgeDays;
	private boolean showHidden;
	
	public long getMaxTweetAgeDays() {
		return maxTweetAgeDays;
	}
	public void setMaxTweetAgeDays(long maxTweetAgeDays) {
		this.maxTweetAgeDays = maxTweetAgeDays;
	}
	public boolean isShowHidden() {
		return showHidden;
	}
	public void setShowHidden(boolean showHidden) {
		this.showHidden = showHidden;
	}
}
