package agh.sr.tweedle.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import agh.sr.tweedle.model.SessionBean;

@Component(value="twitterConnectionUtils") 
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "singleton")
public class TwitterConnectionUtils {

	@Autowired
    private ConnectionRepository connectionRepository;
	
	@Autowired
    private SessionBean sessionBean;
	
	public boolean isConnectedToTwitter(){
    	if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
    		sessionBean.setTwitterProfile(null);
    		return false;
        }
    	else{
    		return true;
    	}
	}
	
}
