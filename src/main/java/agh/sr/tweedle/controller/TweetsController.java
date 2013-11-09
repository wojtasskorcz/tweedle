package agh.sr.tweedle.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
    private Twitter twitter;
	
	@Autowired
    private ConnectionRepository connectionRepository;
	
	@Autowired
    private SessionBean sessionBean;
    
    @RequestMapping("/")
	public String index(Model model) {
    	model.addAttribute("sessionBean", sessionBean);
    	
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
