package agh.sr.tweedle.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import agh.sr.tweedle.model.SessionBean;

@Component("localAuthenticationSuccessHandler")
public class LocalAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private SessionBean sessionBean;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	sessionBean.setSecurityPrincipal(principal);
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
