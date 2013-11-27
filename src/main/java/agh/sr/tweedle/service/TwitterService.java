package agh.sr.tweedle.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import agh.sr.tweedle.dao.UserDao;
import agh.sr.tweedle.model.ExtendedTweet;
import agh.sr.tweedle.model.SessionBean;

/**
 * Service facilitating common operations on {@link agh.sr.tweedle.model.User}.
 */
@Service
public class TwitterService {

	private Twitter twitter;
	private SessionBean sessionBean;
	private UserDao userDao;

	/**
	 * Constructor with all dependencies automatically injected by Spring.
	 * 
	 * @param twitter
	 *            Twitter reference of this user
	 * @param sessionBean
	 *            {@link agh.sr.tweedle.model.SessionBean} identifying the
	 *            current user
	 * @param userDao
	 *            DAO providing database access for
	 *            {@link agh.sr.tweedle.model.User} objects
	 */
	@Autowired
	public TwitterService(Twitter twitter, SessionBean sessionBean,
			UserDao userDao) {
		this.twitter = twitter;
		this.sessionBean = sessionBean;
		this.userDao = userDao;
	}

	/**
	 * Retrieves from Twitter the current list of Tweets according to user's
	 * settings. Retrieves both hidden and visible Tweets and creates
	 * {@link agh.sr.tweedle.model.ExtendedTweet} objects which are then put in
	 * the list and returned. The view determines whether the hidden tweets
	 * should be shown or not.
	 * 
	 * @return list of tweets that will be shown to this user
	 */
	public List<ExtendedTweet> getTweets() {
		Date today = new Date();
		long maxTweetAge = sessionBean.getUser().getMaxTweetAgeDays();
		List<ExtendedTweet> tweets = new ArrayList<ExtendedTweet>();
		Set<Long> hiddenTweets = sessionBean.getUser().getHiddenTweetIds();
		for (Tweet tweet : twitter.timelineOperations().getHomeTimeline()) {
			long tweetAge = daysBetween(today, tweet.getCreatedAt());
			if (tweetAge > maxTweetAge) {
				continue;
			}
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

	/**
	 * Sets the hidden flag for the Tweet. If the Tweet is already hidden and
	 * the flag should be set to true, or if the Tweet is visible and the flagg
	 * should be set to false, will return JSON explaining the exception.
	 * 
	 * @param tweetId
	 *            ID of the tweet for which the flag should be set
	 * @param hidden
	 *            value of the flag
	 * @return JSON describing the status of this operation
	 */
	public String setHidden(Long tweetId, boolean hidden) {
		if (hidden) {
			boolean added = sessionBean.getUser().getHiddenTweetIds()
					.add(tweetId);
			if (!added) {
				return getTweetAlreadyHiddenJson(tweetId);
			}
		} else {
			boolean removed = sessionBean.getUser().getHiddenTweetIds()
					.remove(tweetId);
			if (!removed) {
				return getTweetNotHiddenJson(tweetId);
			}
		}
		userDao.update(sessionBean.getUser());
		return getSuccessJson();
	}

	/**
	 * Returns JSON explaining that the Tweet was already hidden and the flag
	 * could not be set to true once again.
	 * 
	 * @param tweetId
	 *            ID of the tweet for which the flag was attempted to be set
	 * @return JSON describing the exception
	 */
	public static String getTweetAlreadyHiddenJson(long tweetId) {
		return String.format(
				"{\"exception\": \"Tweet %s was already hidden\"}", tweetId);
	}

	/**
	 * Returns JSON explaining that the Tweet was already visible and the flag
	 * could not be set to false once again.
	 * 
	 * @param tweetId
	 *            ID of the tweet for which the flag was attempted to be set
	 * @return JSON describing the exception
	 */
	public static String getTweetNotHiddenJson(long tweetId) {
		return String.format("{\"exception\": \"Tweet %s was not hidden\"",
				tweetId);
	}

	/**
	 * Returns JSON indicating, that setting of the flag went successfully.
	 * 
	 * @return JSON describing empty exception (no exception)
	 */
	public static String getSuccessJson() {
		return "{\"exception\": \"\"}";
	}

	private long daysBetween(Date d1, Date d2) {
		return (long) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}
