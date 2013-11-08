package agh.sr.tweedle.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/twitter/tweets")
public class TwitterTweetsController {
	
	private static final Logger logger = LoggerFactory.getLogger(TwitterTweetsController.class);

	
    private Twitter twitter;
    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterTweetsController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;     
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        List<Tweet> tweets = twitter.timelineOperations().getHomeTimeline();
        model.addAttribute("tweets", tweets);
        
        return "twitter/tweets";
    }
}