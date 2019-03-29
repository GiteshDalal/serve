package com.giteshdalal.authservice.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

/**
 * @author gitesh
 */
@Controller
@SessionAttributes("authorizationRequest")
public class OAuthController {

	@RequestMapping("/oauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {

		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}
		if (!model.containsKey("scopes") && request.getAttribute("scopes") != null) {
			model.put("scopes", request.getAttribute("scopes"));
		}

		return new ModelAndView("oauth/confirm_access", model);
	}

	@RequestMapping("/oauth/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<>();
		Object error = request.getAttribute("error");
		// The error summary may contain malicious user input,
		// it needs to be escaped to prevent XSS
		String errorCode, errorSummary;
		if (error instanceof OAuth2Exception) {
			OAuth2Exception oauthError = (OAuth2Exception) error;
			errorCode = String.valueOf(oauthError.getHttpErrorCode());
			errorSummary = HtmlUtils.htmlEscape(oauthError.getSummary());
		} else {
			errorCode = "???";
			errorSummary = "Unknown error";
		}
		model.put("errorCode", errorCode);
		model.put("errorSummary", errorSummary);
		return new ModelAndView("oauth/error", model);
	}
}
