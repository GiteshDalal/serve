package com.giteshdalal.authservice.controller;

import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;

import com.giteshdalal.authservice.email.template.EmailTemplate;
import com.giteshdalal.authservice.email.template.impl.UserRegisteredEmailTemplate;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.resource.UserResource;
import com.giteshdalal.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("authorizationRequest")
public class RegisterController {

	@Autowired
	UserService userService;

	@Autowired
	EmailTemplate<UserResource> userRegisteredEmailTemplate;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerUserPage(Map<String, Object> model, HttpServletRequest request) {

		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}

		return new ModelAndView("register", model);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@RequestParam Map<String, String> form, Map<String, Object> model, HttpServletRequest request, Locale locale) {

		final String username = form.get("username");
		final String email = form.get("email");

		try {
			UserModel registered = userService.register(username, email, form.get("password"));
			UserResource user = new UserResource();
			user.setEmail(registered.getEmail());
			user.setUsername(registered.getUsername());
			userRegisteredEmailTemplate.send(user, locale);
			model.put("msg", String.format("User [%s] registered successfully.", registered.getUsername()));
			return new ModelAndView("success", model);
		} catch (AccountException | MessagingException e) {
			model.put("error", e.getMessage());
		}

		return new ModelAndView("register", model);
	}
}
