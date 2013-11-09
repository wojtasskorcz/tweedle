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

import agh.sr.tweedle.model.SessionBean;


@Controller
@RequestMapping("/followed")
public class FollowedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FollowedController.class);

	
    private Twitter twitter;
    private ConnectionRepository connectionRepository;
    private SessionBean sessionBean;
    
    @Inject
    public FollowedController(Twitter twitter, ConnectionRepository connectionRepository, SessionBean sessionBean) {
        this.twitter = twitter;     
        this.connectionRepository = connectionRepository;
        this.sessionBean = sessionBean;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        model.addAttribute("sessionBean", sessionBean);
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        
        model.addAttribute("friends", friends);
        return "twitter/followed";
    }
}
