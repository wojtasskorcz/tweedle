package agh.sr.tweedle.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import agh.sr.tweedle.dao.UserDao;
import agh.sr.tweedle.model.OptionsForm;
import agh.sr.tweedle.model.SessionBean;

/**
 * Controls the flow of "/options" URLs.
 */
@Controller
@RequestMapping("/options")
public class OptionsController {

	private static final Logger logger = Logger
			.getLogger(OptionsController.class.getName());

	@Autowired
	private SessionBean sessionBean;

	@Autowired
	private UserDao userDao;

	/**
	 * Adds the {@link agh.sr.tweedle.model.OptionsForm} to the model when this
	 * controller processes requests.
	 * 
	 * @return {@link agh.sr.tweedle.model.OptionsForm} object that should be
	 *         added to the model
	 */
	@ModelAttribute("optionsForm")
	public OptionsForm getOptionsForm() {
		OptionsForm form = new OptionsForm();
		form.setMaxTweetAgeDays(sessionBean.getUser().getMaxTweetAgeDays());
		form.setShowHidden(sessionBean.getUser().getShowHidden());
		return form;
	}

	/**
	 * Requests rendering the main view of options.
	 * 
	 * @param model
	 *            model that backs up the view
	 * @return name of the view to be rendered
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getOptions(Model model) {
		model.addAttribute("sessionBean", sessionBean);
		return "options";
	}

	/**
	 * Updates user options according to the received
	 * {@link agh.sr.tweedle.model.OptionsForm}.
	 * 
	 * @param optionsForm
	 *            form containing new settings for the user
	 * @param result
	 *            result of the validation of the form
	 * @param redirectAttributes
	 *            attributes used to carry model information after redirecting
	 * @return string that instructs Spring to redirect to "/options"
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String changeOptions(
			@Valid @ModelAttribute("optionsForm") OptionsForm optionsForm,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(
					"org.springframework.validation.BindingResult.optionsForm",
					result);
			redirectAttributes.addFlashAttribute("optionsForm", optionsForm);
			return "redirect:/options";
		}
		sessionBean.getUser().setShowHidden(optionsForm.isShowHidden());
		sessionBean.getUser().setMaxTweetAgeDays(
				optionsForm.getMaxTweetAgeDays());
		try {
			userDao.update(sessionBean.getUser());
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
			redirectAttributes.addFlashAttribute("exception", e.toString());
			return "redirect:/options";
		}
		redirectAttributes.addFlashAttribute("optionsChanged",
				"Options changed.");
		return "redirect:/options";
	}
}
