package agh.sr.tweedle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import agh.sr.tweedle.model.ExtendedTweet;
import agh.sr.tweedle.model.SessionBean;

@Service
public class TwitterService {
	
	@Autowired
	private Twitter twitter;
	
	@Autowired
	private SessionBean sessionBean;
	
	public TwitterService() {}
	
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
	
	// for testing purposes
	public TwitterService(Twitter twitter, SessionBean sessionBean) {
		this.twitter = twitter;
		this.sessionBean = sessionBean;
	}

}
