package agh.sr.tweedle.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import agh.sr.tweedle.model.FollowUserForm;
import agh.sr.tweedle.model.SessionBean;
import agh.sr.tweedle.util.TwitterConnectionUtils;
import agh.sr.tweedle.validation.FollowUserFormValidator;

/**
 * Controls the flow of "/followed" URLs.
 */
@Controller
@RequestMapping("/followed")
public class FollowedController {

	private static final Logger logger = Logger
			.getLogger(FollowedController.class.getName());

	@Autowired
	private Twitter twitter;

	@Autowired
	private FollowUserFormValidator followUserFormValidator;

	@Autowired
	private TwitterConnectionUtils twitterConnectionUtils;

	@Autowired
	private SessionBean sessionBean;

	/**
	 * Adds the {@link agh.sr.tweedle.model.FollowUserForm} to the model when
	 * this controller processes requests.
	 * 
	 * @return {@link agh.sr.tweedle.model.FollowUserForm} object that should be
	 *         added to the model
	 */
	@ModelAttribute("followedForm")
	public FollowUserForm newFollowedForm() {
		return new FollowUserForm();
	}

	/**
	 * Requests rendering of the view that shows users followed on Twitter by
	 * the user. If user is not connected to Twitter, returns Twitter logging
	 * view.
	 * 
	 * @param model
	 *            current model (injected by Spring)
	 * @return name of the view to be rendered
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getFollowed(Model model) {
		model.addAttribute("sessionBean", sessionBean);
		CursoredList<TwitterProfile> followed = null;
		try {
			if (!twitterConnectionUtils.isConnectedToTwitter()) {
				return "redirect:/connect/twitter";
			}
			followed = twitter.friendOperations().getFriends();
		} catch (Exception e) {
			model.addAttribute("exception", e.toString());
			return "index";
		}

		model.addAttribute("followed", followed);
		return "twitter/followed";
	}

	/**
	 * Sets the validator for this controller to
	 * {@link agh.sr.tweedle.validation.FollowUserFormValidator}.
	 * 
	 * @param binder
	 *            a WebDataBinder that binds web request parameters to JavaBean
	 *            objects
	 */
	@InitBinder("followedForm")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(followUserFormValidator);
	}

	/**
	 * Processes adding a new followed user. After the operation finishes
	 * (successfully or not) returns the same view.
	 * 
	 * @param followedForm
	 *            form containing the name of the user that is to be added
	 * @param result
	 *            result of the validation of the form
	 * @param redirectAttributes
	 *            attributes used to carry model information after redirecting
	 * @return string that instructs Spring to redirect to "/followed"
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String addFollowed(
			@Valid @ModelAttribute("followedForm") FollowUserForm followedForm,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes
					.addFlashAttribute(
							"org.springframework.validation.BindingResult.followedForm",
							result);
			redirectAttributes.addFlashAttribute("followedForm", followedForm);
			return "redirect:/followed";
		}

		try {
			twitter.friendOperations().follow(followedForm.getScreenName());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("exception", e.toString());
			return "redirect:/followed";
		}
		return "redirect:/followed";
	}

	/**
	 * Processes a request to remove a followed user.
	 * @param userId ID of the user that is to be removed
	 * @return JSON with the status of the operation
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String removeFollowed(@PathVariable("id") String userId) {
		Long id = Long.parseLong(userId);
		try {
			twitter.friendOperations().unfollow(id);
		} catch (Exception e) {
			return String.format("{\"exception\": \"Remove user %ld failed\"}",
					id);
		}

		return "{\"ok\": \"true\"}";
	}
}
