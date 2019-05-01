package com.giteshdalal.authservice.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.giteshdalal.authservice.ApplicationConfiguration;
import com.giteshdalal.authservice.email.template.EmailTemplate;
import com.giteshdalal.authservice.email.template.impl.ResetTokenEmailTemplate;
import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.resource.UserResource;
import com.giteshdalal.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("authorizationRequest")
public class ResetPasswordController {

	@Autowired
	UserService userService;

	@Autowired
	EmailTemplate<UserResource> userRegisteredEmailTemplate;

	@Autowired
	ResetTokenEmailTemplate resetTokenEmailTemplate;

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public ModelAndView forgotPasswordPage(Map<String, Object> model, HttpServletRequest request) {
		return new ModelAndView("forgot-password", model);
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ModelAndView forgotPassword(@RequestParam Map<String, String> form, Map<String, Object> model, HttpServletRequest request,
			Locale locale) {
		final String username = form.get("username");

		try {
			model.put("username", username);
			UserModel user = userService.findUserByUsername(username);
			final String token = userService.generateResetToken(user);
			final String email = user.getEmail();
			final Map<String, String> context = new HashMap();
			context.put("email", email);
			context.put("token", token);
			context.put("origin", request.getHeader("origin"));
			resetTokenEmailTemplate.send(context, locale);

			model.put("msg", "Reset token sent to registered email successfully.");
		} catch (UsernameNotFoundException e) {
			model.put("msg", "Invalid username.");
		} catch (MessagingException e) {
			model.put("msg", "Encountered error while sending an email. Please try again later.");
		}
		return new ModelAndView("forgot-password", model);
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public ModelAndView resetPasswordPage(Map<String, Object> model, HttpServletRequest request) {
		model.put("tokenLength", ApplicationConfiguration.RESET_TOKEN_LENGTH);
		return new ModelAndView("reset-password", model);
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetPassword(@RequestParam Map<String, String> form, Map<String, Object> model, HttpServletRequest request,
			Locale locale) {
		final String token = form.get("token");
		final String password = form.get("password");

		try {
			userService.resetPassword(token, password);
			model.put("msg", String.format("Password changed successfully."));
			return new ModelAndView("success", model);
		} catch (NotFoundAuthServiceException e) {
			model.put("msg", e.getMessage());
			model.put("tokenLength", ApplicationConfiguration.RESET_TOKEN_LENGTH);
		}
		return new ModelAndView("reset-password", model);
	}
}
