package com.giteshdalal.authservice.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("authorizationRequest")
@RequestMapping("/client")
public class ClientController {

	@RequestMapping("/update")
	public ModelAndView registerUser(Map<String, Object> model, HttpServletRequest request) {

		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}

		return new ModelAndView("register", model);
	}
}
