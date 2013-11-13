package agh.sr.tweedle.controller;

import java.util.logging.Logger;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.util.TwitterConnectionUtils;


@Controller
@RequestMapping("/followed")
public class FollowedController {
	
	private static final Logger logger = Logger.getLogger(FollowedController.class.getName());

	@Autowired
    private Twitter twitter;
	
	@Autowired
    private TwitterConnectionUtils twitterConnectionUtils;
	
	@Autowired
    private SessionBean sessionBean;

    @RequestMapping(method=RequestMethod.GET)
    public String getFollowed(Model model) {
        model.addAttribute("sessionBean", sessionBean);
        CursoredList<TwitterProfile> followed = null;
        try{
            if (!twitterConnectionUtils.isConnectedToTwitter()) {
        		return "redirect:/connect/twitter";
            }	
            followed = twitter.friendOperations().getFriends();
        }
        catch (Exception e){
			model.addAttribute("exception", e.toString());
			return "index";
        }
    
        model.addAttribute("followed", followed);      
        return "twitter/followed";
    }
}
