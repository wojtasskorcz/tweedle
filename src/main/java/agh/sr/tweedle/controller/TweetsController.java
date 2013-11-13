package agh.sr.tweedle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.exception.ExceptionUtils;
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

	private static final Logger logger = Logger.getLogger(TweetsController.class.getName());

	@Autowired
	private Twitter twitter;

	@Autowired
	private TwitterConnectionUtils twitterConnectionUtils;

	@Autowired
	private SessionBean sessionBean;

	@RequestMapping("/")
	public String index(Model model) {
		logger.warning("index");
		model.addAttribute("sessionBean", sessionBean);
		List<Tweet> tweets = new ArrayList<Tweet>();

		try {
			if (!twitterConnectionUtils.isConnectedToTwitter()) {
				return "index";
			}
			tweets.addAll(twitter.timelineOperations().getHomeTimeline());
		} catch (Exception e) {
			logger.warning(ExceptionUtils.getStackTrace(e));
			model.addAttribute("exception", e.toString());
			return "index";
		}

		model.addAttribute("tweets", tweets);
		return "twitter/tweets";
	}
}
