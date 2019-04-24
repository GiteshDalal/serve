package com.giteshdalal.authservice.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("authorizationRequest")
public class ManagementController {

	@RequestMapping("dashboard")
	public ModelAndView dashboard(Map<String, Object> model, HttpServletRequest request) {

		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}

		return new ModelAndView("dashboard", model);
	}
}
