package agh.sr.tweedle.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import agh.sr.tweedle.model.FollowUserForm;

@Component
public class FollowUserFormValidator implements Validator {
	
	@Autowired
	private Twitter twitter;

	@Override
	public boolean supports(Class<?> clazz) {
		return FollowUserForm.class.isAssignableFrom(clazz);  
	}

	@Override
	public void validate(Object target, Errors errors) {
		FollowUserForm form = (FollowUserForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "screenName", "followuserform.screenname.empty", "Screen Name field is empty.");
		
		if (form.getScreenName() != null && form.getScreenName().length() != 0){
			try{
				twitter.userOperations().getUserProfile(form.getScreenName());
			}
			catch(Exception e){
				errors.rejectValue("screenName", "followuserform.screenname.doesntexist", "User account doesn't exist on twitter.");
			}
		}
		
	}

}
