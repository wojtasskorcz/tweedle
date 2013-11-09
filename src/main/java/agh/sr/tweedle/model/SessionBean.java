package agh.sr.tweedle.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

@Component(value="sessionBean") @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class SessionBean implements Serializable {
	private TwitterProfile twitterProfile;
	private Object securityPrincipal;
	
	public TwitterProfile getTwitterProfile() {
		return twitterProfile;
	}
	public void setTwitterProfile(TwitterProfile twitterProfile) {
		this.twitterProfile = twitterProfile;
	}
	public Object getSecurityPrincipal() {
		return securityPrincipal;
	}
	public void setSecurityPrincipal(Object securityPrincipal) {
		this.securityPrincipal = securityPrincipal;
	}
}
