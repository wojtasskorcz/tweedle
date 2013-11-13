package agh.sr.tweedle.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class SecurityController {
	
	private static final Logger logger = Logger.getLogger(SecurityController.class.getName());

	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}
	
    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "security/login";
    }
	
}
