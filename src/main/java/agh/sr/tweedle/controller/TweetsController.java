package agh.sr.tweedle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.util.TwitterConnectionUtils;

import com.jayway.restassured.path.json.JsonPath;

@Controller
@RequestMapping("/")
public class TweetsController {

	private static final Logger logger = Logger.getLogger(TweetsController.class.getName());

	@Autowired
	private Twitter twitter;

	@Autowired
	private TwitterConnectionUtils twitterConnectionUtils;

	@Autowired
	private SessionBean sessionBean;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("sessionBean", sessionBean);
		List<Tweet> tweets = new ArrayList<Tweet>();

		try {
			if (!twitterConnectionUtils.isConnectedToTwitter()) {
				return "index";
			}
			tweets.addAll(twitter.timelineOperations().getHomeTimeline());
		} catch (Exception e) {
			model.addAttribute("exception", e.toString());
			return "index";
		}

		model.addAttribute("tweets", tweets);
		return "twitter/tweets";
	}
	
	@RequestMapping(value = "/toggleHidden", method = RequestMethod.POST, 
			consumes="application/json; charset=utf-8", 
			produces="application/json; charset=utf-8")
	@ResponseBody
	public String toggleHidden(@RequestBody String json) {
		logger.warning("received JSON: " + json);
		long tweetId = JsonPath.with(json).getLong("id");
		boolean hidden = JsonPath.with(json).getBoolean("hidden");
		logger.warning(String.format("parsed JSON: id=%s, hidden=%s", tweetId, hidden));
		if (hidden) {
			boolean added = sessionBean.getUser().getReadTweetIds().add(tweetId);
			if (!added) {
				return String.format("{\"exception\": \"Tweet %s was already hidden\"}", tweetId);
			}
		} else {
			boolean removed = sessionBean.getUser().getReadTweetIds().remove(tweetId);
			if (!removed) {
				return String.format("{\"exception\": \"Tweet %s was not hidden\"", tweetId);
			}
		}
		return "{\"exception\": \"\"}";
	}
}
