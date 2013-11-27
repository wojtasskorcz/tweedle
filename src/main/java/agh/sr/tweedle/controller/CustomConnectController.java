package agh.sr.tweedle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that intercepts Twitter connection changes and allows for
 * implementation of custom logic when they arise. This implementation of the
 * controller redirects user to "/" after connection status changes.
 */
@Controller
public class CustomConnectController extends ConnectController {

	/**
	 * Creates this controller.
	 * 
	 * @param connectionFactoryLocator
	 *            the locator for ConnectionFactory instances needed to
	 *            establish connections
	 * @param connectionRepository
	 *            the current user's ConnectionRepository needed to persist
	 *            connections; must be a proxy to a request-scoped bean
	 */
	
	@Autowired
	public CustomConnectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		super(connectionFactoryLocator, connectionRepository);
	}

	@Override
	protected String connectedView(String providerId) {
		return "redirect:/";
	}

	@Override
	protected String connectView(String providerId) {
		return "redirect:/";
	}

	@Override
	protected String connectView() {
		return "redirect:/";
	}
}
