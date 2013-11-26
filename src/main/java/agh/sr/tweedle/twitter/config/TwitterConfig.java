package agh.sr.tweedle.twitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableJdbcConnectionRepository;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.twitter.config.annotation.EnableTwitter;

import agh.sr.tweedle.controller.CustomConnectController;
import agh.sr.tweedle.util.TwitterConnectInterceptor;

/**
 * Spring JavaConfig for Spring Social Twitter components.
 */
@Configuration
@EnableTwitter(appId = "tM9168yNESE2wDb06nZOGw", appSecret = "rFtCY3e6hSMAGY6YegJPooILdld21gQEl8zTi4")
@EnableJdbcConnectionRepository
public class TwitterConfig {

	@Autowired
	private TwitterConnectInterceptor twitterConnectInterceptor;

	/**
	 * Creates a bean that returns current user's ID. This is implemented using
	 * Spring Security's Authentication class.
	 * 
	 * @return new bean enabling Twitter to distinguish users from one another
	 */
	@Bean
	public UserIdSource userIdSource() {
		return new UserIdSource() {
			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder
						.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException(
							"Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}

	/**
	 * Creates ConntectController bean which serves as the main mechanism
	 * managing connections to Twitter.
	 * 
	 * @param connectionFactoryLocator
	 *            Spring Social's ConnectionFactoryLocator (automatically
	 *            injected)
	 * @param connectionRepository
	 *            Spring Social's ConnectionRepository (automatically injected)
	 * @return new bean that orchestrates connections to Twitter
	 */
	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		CustomConnectController connectionController = new CustomConnectController(
				connectionFactoryLocator, connectionRepository);
		connectionController.addInterceptor(twitterConnectInterceptor);
		return connectionController;
	}

	/**
	 * Creates TextEncryptor bean which is used to encrypt users' secrets in
	 * database when storing persistent connection to Twitter.
	 * 
	 * @return new bean that encrypts users' secrets in database
	 */
	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.noOpText();
	}

}