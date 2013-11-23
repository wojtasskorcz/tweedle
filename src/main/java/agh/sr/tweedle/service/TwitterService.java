package agh.sr.tweedle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import agh.sr.tweedle.dao.UserDao;
import agh.sr.tweedle.model.ExtendedTweet;
import agh.sr.tweedle.model.SessionBean;

@Service
public class TwitterService {
	public final static String TWEET_ALREADY_HIDDEN_JSON = 
			"{\"exception\": \"Tweet %s was already hidden\"}";
	public final static String TWEET_NOT_HIDDEN_JSON = 
			"{\"exception\": \"Tweet %s was not hidden\"";
	public final static String SUCCESS_JSON = "{\"exception\": \"\"}";
	
	private Twitter twitter;
	private SessionBean sessionBean;
	private UserDao userDao;
	
	@Autowired
	public TwitterService(Twitter twitter, SessionBean sessionBean, UserDao userDao) {
		this.twitter = twitter;
		this.sessionBean = sessionBean;
		this.userDao = userDao;
	}
	
	public List<ExtendedTweet> getTweets() {
		List<ExtendedTweet> tweets = new ArrayList<ExtendedTweet>();
		Set<Long> hiddenTweets = sessionBean.getUser().getHiddenTweetIds();
		for (Tweet tweet : twitter.timelineOperations().getHomeTimeline()) {
			ExtendedTweet extTweet = null;
			if (hiddenTweets.contains(tweet.getId())) {
				extTweet = new ExtendedTweet(tweet, true);
			} else {
				extTweet = new ExtendedTweet(tweet, false);
			}
			tweets.add(extTweet);
		}
		return tweets;
	}
	
	public String setHidden(Long tweetId, boolean hidden) {
		if (hidden) {
			boolean added = sessionBean.getUser().getHiddenTweetIds().add(tweetId);
			if (!added) {
				return getTweetAlreadyHiddenJson(tweetId);
			}
		} else {
			boolean removed = sessionBean.getUser().getHiddenTweetIds().remove(tweetId);
			if (!removed) {
				return getTweetNotHiddenJson(tweetId);
			}
		}
		userDao.update(sessionBean.getUser());
		return getSuccessJson();
	}
	
	public static String getTweetAlreadyHiddenJson(long tweetId) {
		return String.format("{\"exception\": \"Tweet %s was already hidden\"}", tweetId);
	}
	
	public static String getTweetNotHiddenJson(long tweetId) {
		return String.format("{\"exception\": \"Tweet %s was not hidden\"", tweetId);
	}
	
	public static String getSuccessJson() {
		return "{\"exception\": \"\"}";
	}

}
