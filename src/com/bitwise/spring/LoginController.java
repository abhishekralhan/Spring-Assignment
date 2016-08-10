package com.bitwise.spring;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bitwise.model.LoginValidator;
import com.bitwise.model.UserCredentials;

@Controller
// @SessionAttributes("username")
public class LoginController {

	@Autowired
	LoginValidator validator;

	@InitBinder("UserCredentials")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginValidator());
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("UserLogin", "command", new UserCredentials());
	}

	@RequestMapping(value = "/Homepage", method = RequestMethod.POST)
	public String validateUser(@ModelAttribute("credential") UserCredentials credentials, BindingResult result,
			ModelMap model, HttpSession session, final RedirectAttributes redirectAttributes) {
		LoginValidator userValidator = new LoginValidator();
		userValidator.validate(credentials, result);
		if (result.hasErrors()) {
			return "redirect:/";

		} else {

			if (credentials.Validate(credentials.getUserName(), credentials.getPassword())) {

				session.setAttribute("username", credentials.getUserName());
				model.addAttribute("userName", credentials.getUserName());
				return "Homepage";
			} else {
				redirectAttributes.addFlashAttribute("message", "Wrong Username/Password.");
				return "redirect:/";
			}
		}
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String logout(final RedirectAttributes redirectAttributes, HttpSession session) {
		session.invalidate();
		redirectAttributes.addFlashAttribute("message", "You have been successfully logged out.");
		return "redirect:/";
	}

}
