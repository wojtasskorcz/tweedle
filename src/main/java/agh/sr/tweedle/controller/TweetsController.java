package agh.sr.tweedle.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class TweetsController {
	
	private static final Logger logger = LoggerFactory.getLogger(TweetsController.class);

	
    private Twitter twitter;
    private ConnectionRepository connectionRepository;

    @Inject
    public TweetsController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;     
        this.connectionRepository = connectionRepository;
    }
    
    @RequestMapping("/")
	public String index(Model model) {
    	if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
    		return "index";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        List<Tweet> tweets = twitter.timelineOperations().getHomeTimeline();
        model.addAttribute("tweets", tweets);
        
        return "twitter/tweets";
	}
}
