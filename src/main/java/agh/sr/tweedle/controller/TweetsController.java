package agh.sr.tweedle.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.util.TwitterConnectionUtils;


@Controller
@RequestMapping("/")
public class TweetsController {
	
	private static final Logger logger = LoggerFactory.getLogger(TweetsController.class);

	@Autowired
    private Twitter twitter;
	
	@Autowired
    private TwitterConnectionUtils twitterConnectionUtils;
	
	@Autowired
    private SessionBean sessionBean;
    
    @RequestMapping("/")
	public String index(Model model) {
    	model.addAttribute("sessionBean", sessionBean);
    	
    	if (!twitterConnectionUtils.isConnectedToTwitter()) {
    		return "index";
        }	
        
        List<Tweet> tweets = twitter.timelineOperations().getHomeTimeline();
        model.addAttribute("tweets", tweets);
        
        return "twitter/tweets";
	}
}
