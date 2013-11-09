package agh.sr.tweedle.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import agh.sr.tweedle.model.SessionBean;


@Controller
@RequestMapping("/")
public class TweetsController {
	
	private static final Logger logger = LoggerFactory.getLogger(TweetsController.class);

	
    private Twitter twitter;
    private ConnectionRepository connectionRepository;
    private SessionBean sessionBean;

    @Inject
    public TweetsController(Twitter twitter, ConnectionRepository connectionRepository, SessionBean sessionBean) {
        this.twitter = twitter;     
        this.connectionRepository = connectionRepository;
        this.sessionBean = sessionBean;
    }
    
    @RequestMapping("/")
	public String index(Model model) {
    	model.addAttribute("sessionBean", sessionBean);
    	if (sessionBean.getSecurityPrincipal() == null){ //TODO: extract this to some spring security after login hook
    		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		sessionBean.setSecurityPrincipal(principal);
    	}
    	
    	if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
    		return "index";
        }
    	
    	if (sessionBean.getTwitterProfile() == null){ //TODO: extract this to after twitter login hook
    		sessionBean.setTwitterProfile(twitter.userOperations().getUserProfile());
    	}
    	
        
        List<Tweet> tweets = twitter.timelineOperations().getHomeTimeline();
        model.addAttribute("tweets", tweets);
        
        return "twitter/tweets";
	}
}
