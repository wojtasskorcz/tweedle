package agh.sr.tweedle.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agh.sr.tweedle.form.CredentialsForm;

@Controller
public class LoginController {
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("credentials", new CredentialsForm());
		return "index";
	}
	
	@RequestMapping(value="/doLogin", method=RequestMethod.POST)
	public String doLogin(@ModelAttribute("credentials") CredentialsForm credentials) {
		// TODO remove, checking java 1.7 compliance
		List<String> list = new ArrayList<>();
		list.add("admin");
		if (credentials.getLogin().equals(list.get(0)) &&
				credentials.getPassword().equals(list.get(0))) {
			return "welcome";
		} else {
			return "index";
		}
	}

}
