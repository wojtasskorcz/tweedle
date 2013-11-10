package agh.sr.tweedle.twitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableInMemoryConnectionRepository;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.twitter.config.annotation.EnableTwitter;

import agh.sr.tweedle.controller.CustomConnectController;
import agh.sr.tweedle.util.TwitterConnectInterceptor;

@Configuration
@EnableTwitter(appId="tM9168yNESE2wDb06nZOGw", appSecret="rFtCY3e6hSMAGY6YegJPooILdld21gQEl8zTi4")
@EnableInMemoryConnectionRepository
public class TwitterConfig {
	
	@Autowired
	private TwitterConnectInterceptor twitterConnectInterceptor;

    @Bean
    public UserIdSource userIdSource() {
        return new UserIdSource() {         
            @Override
            public String getUserId() {
                return "WujekFranek666";
            }
        };
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        CustomConnectController connectionController = new CustomConnectController(connectionFactoryLocator, connectionRepository);
        connectionController.addInterceptor(twitterConnectInterceptor);
    	return connectionController;
    }

}
