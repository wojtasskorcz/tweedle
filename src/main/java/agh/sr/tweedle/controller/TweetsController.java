package agh.sr.tweedle.controller;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import agh.sr.tweedle.model.ExtendedTweet;
import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.service.TwitterService;
import agh.sr.tweedle.util.TwitterConnectionUtils;

import com.jayway.restassured.path.json.JsonPath;

/**
 * Controller that orchestrates the interactions with the main view of the
 * application.
 */
@Controller
@RequestMapping("/")
public class TweetsController {

	private static final Logger logger = Logger
			.getLogger(TweetsController.class.getName());

	@Autowired
	private TwitterConnectionUtils twitterConnectionUtils;

	@Autowired
	private SessionBean sessionBean;

	@Autowired
	private TwitterService twitterService;

	/**
	 * Prepares tweets to be displayed and adds them to the model.
	 * 
	 * @param model
	 *            model that will back up the view
	 * @return the name of the view
	 */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("sessionBean", sessionBean);
		List<ExtendedTweet> tweets = null;

		try {
			if (!twitterConnectionUtils.isConnectedToTwitter()) {
				return "index";
			}
			tweets = twitterService.getTweets();
		} catch (Exception e) {
			model.addAttribute("exception", e.toString());
			return "index";
		}

		model.addAttribute("tweets", tweets);
		return "twitter/tweets";
	}

	/**
	 * Sets tweet's hidden flag according to the requested state. Adds
	 * information about the operation result to the model.
	 * 
	 * @param json
	 *            JSON determining the tweet and its hidden flag
	 * @param redirectAttributes
	 *            attributes used to carry model information after redirecting
	 * @return string redirecting to the root URL
	 */
	@RequestMapping(value = "/toggleHidden", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String toggleHidden(@RequestBody String json,
			final RedirectAttributes redirectAttributes) {
		long tweetId = Long.parseLong(JsonPath.with(json).getString("id"));
		boolean hidden = JsonPath.with(json).getBoolean("hidden");
		try {
			return twitterService.setHidden(tweetId, hidden);
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
			redirectAttributes.addFlashAttribute("exception", e.toString());
			return "redirect:/";
		}
	}
}
