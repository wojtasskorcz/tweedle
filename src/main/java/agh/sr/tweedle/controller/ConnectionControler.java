package agh.sr.tweedle.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agh.sr.tweedle.model.SessionBean;

@Controller
@RequestMapping("/twitter-logout")
public class ConnectionControler {

	@Autowired
	private SessionBean sessionBean;

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model)
			throws IOException {
		String baseUrl = request.getRequestURL().toString().replaceFirst("/twitter-logout", "/connect/twitter");
		URL url = new URL(baseUrl);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
		return "redirect:/";
	}
}
