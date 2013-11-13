package agh.sr.tweedle.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class SecurityController {
	
	private static final Logger logger = Logger.getLogger(SecurityController.class.getName());

	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}
	
}
