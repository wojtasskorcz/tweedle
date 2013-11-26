package agh.sr.tweedle.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that orchestrates the application after successful and
 * unsuccessful login attempt.
 */
@Controller("/")
public class SecurityController {

	private static final Logger logger = Logger
			.getLogger(SecurityController.class.getName());

	/**
	 * Determines, where the applications should proceed after successful login.
	 * 
	 * @return the view to be rendered
	 */
	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}

	/**
	 * Determines, where the applications should proceed after unsuccessful
	 * login.
	 * 
	 * @param model
	 *            model that will carry the message about unsuccessful login
	 *            attempt
	 * @return the view to be rendered
	 */
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "security/login";
	}

}
