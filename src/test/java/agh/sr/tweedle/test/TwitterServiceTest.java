package agh.sr.tweedle.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import agh.sr.tweedle.dao.UserDao;
import agh.sr.tweedle.model.ExtendedTweet;
import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.model.User;
import agh.sr.tweedle.service.TwitterService;

public class TwitterServiceTest {
	
	private List<Tweet> tweets;
	private Set<Long> hiddenTweetIds;
	private Twitter twitter;
	private SessionBean sessionBean;
	private TwitterService twitterService;
	private UserDao userDao;
	
	// initialize tweets and hiddenTweetIds to empty collections 
	// and bind them to twitter and sessionBean
	@Before 
	public void setup() {
		tweets = new ArrayList<>();
		hiddenTweetIds = new HashSet<>();
		
		twitter = mock(Twitter.class);
		TimelineOperations timelineOperations = mock(TimelineOperations.class);
		when(twitter.timelineOperations()).thenReturn(timelineOperations);
		when(twitter.timelineOperations().getHomeTimeline()).thenReturn(tweets);
		
		sessionBean = mock(SessionBean.class);
		User user = mock(User.class);
		when(sessionBean.getUser()).thenReturn(user);
		when(user.getHiddenTweetIds()).thenReturn(hiddenTweetIds);
		
		userDao = mock(UserDao.class);
		
		twitterService = new TwitterService(twitter, sessionBean, userDao);
	}
	
	@Test
	public void thatNonHiddenTweetsAreReturned() {
		Tweet tweet = mock(Tweet.class);
		when(tweet.getCreatedAt()).thenReturn(new Date());
		tweets.add(tweet);
		List<ExtendedTweet> returnedTweets = twitterService.getTweets();
		
		assertEquals(1, returnedTweets.size());
		assertEquals(tweet.getId(), returnedTweets.get(0).getId());
		assertFalse(returnedTweets.get(0).isHidden());
	}
	
	@Test
	public void thatHiddenTweetsAreReturned() {
		Tweet tweet = mock(Tweet.class);
		when(tweet.getCreatedAt()).thenReturn(new Date());
		tweets.add(tweet);
		hiddenTweetIds.add(tweet.getId());
		List<ExtendedTweet> returnedTweets = twitterService.getTweets();
		
		assertEquals(1, returnedTweets.size());
		assertEquals(tweet.getId(), returnedTweets.get(0).getId());
		assertTrue(returnedTweets.get(0).isHidden());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void thatOldTweetsAreNotReturned() {
		Tweet tweet = mock(Tweet.class);
		when(tweet.getCreatedAt()).thenReturn(new Date(2013, 10, 1));
		tweets.add(tweet);
		when(sessionBean.getUser().getMaxTweetAgeDays()).thenReturn(7L);
		List<ExtendedTweet> returnedTweets = twitterService.getTweets();
		
		assertEquals(0, returnedTweets.size());
	}
	
	@Test
	public void thatVisibleTweetsGetHidden() {
		doNothing().when(userDao).update(any(User.class));
		Tweet tweet = mock(Tweet.class);
		ExtendedTweet visibleTweet = new ExtendedTweet(tweet, false);
		String result = twitterService.setHidden(visibleTweet.getId(), true);
		
		assertEquals(TwitterService.getSuccessJson(), result);
	}
	
	@Test
	public void thatVisibleTweetsCannotGetVisible() {
		doNothing().when(userDao).update(any(User.class));
		Tweet tweet = mock(Tweet.class);
		ExtendedTweet visibleTweet = new ExtendedTweet(tweet, false);
		String result = twitterService.setHidden(visibleTweet.getId(), false);
		
		assertEquals(TwitterService.getTweetNotHiddenJson(visibleTweet.getId()), result);
	}
	
	@Test
	public void thatHiddenTweetsGetVisible() {
		doNothing().when(userDao).update(any(User.class));
		Tweet tweet = mock(Tweet.class);
		ExtendedTweet hiddenTweet = new ExtendedTweet(tweet, true);
		hiddenTweetIds.add(hiddenTweet.getId());
		String result = twitterService.setHidden(hiddenTweet.getId(), false);
		
		assertEquals(TwitterService.getSuccessJson(), result);
	}
	
	@Test
	public void thatHiddenTweetsCannotGetHidden() {
		doNothing().when(userDao).update(any(User.class));
		Tweet tweet = mock(Tweet.class);
		ExtendedTweet hiddenTweet = new ExtendedTweet(tweet, true);
		hiddenTweetIds.add(hiddenTweet.getId());
		String result = twitterService.setHidden(hiddenTweet.getId(), true);
		
		assertEquals(TwitterService.getTweetAlreadyHiddenJson(hiddenTweet.getId()), result);
	}
	
	@Test
	public void thatUserOptionsGetUpdatedInDatabase() {
		doNothing().when(userDao).update(any(User.class));
		Tweet tweet = mock(Tweet.class);
		ExtendedTweet visibleTweet = new ExtendedTweet(tweet, false);
		twitterService.setHidden(visibleTweet.getId(), false);
		
		verify(userDao).update(sessionBean.getUser());
	}

}
