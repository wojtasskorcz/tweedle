package agh.sr.tweedle.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.model.User;
import agh.sr.tweedle.service.TwitterService;

public class TwitterServiceTest {
	
	private List<Tweet> tweets;
	private Set<Long> hiddenTweetIds;
	private Twitter twitter;
	private SessionBean sessionBean;
	private TwitterService twitterService;
	
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
		
		twitterService = new TwitterService(twitter, sessionBean);
	}
	
	@Test
	public void thatNonHiddenTweetsAreReturned() {
		Tweet tweet = mock(Tweet.class);
		tweets.add(tweet);
		
		assertEquals(twitterService.getTweets().size(), 1);
		assertEquals(twitterService.getTweets().get(0).getId(), tweet.getId());
		assertFalse(twitterService.getTweets().get(0).isHidden());
	}
	
	@Test
	public void thatHiddenTweetsAreReturned() {
		Tweet tweet = mock(Tweet.class);
		tweets.add(tweet);
		hiddenTweetIds.add(tweet.getId());
		
		assertEquals(twitterService.getTweets().size(), 1);
		assertEquals(twitterService.getTweets().get(0).getId(), tweet.getId());
		assertTrue(twitterService.getTweets().get(0).isHidden());
	}

}
