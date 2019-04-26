package com.giteshdalal.authservice.controller;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.resource.RoleResource;
import com.giteshdalal.authservice.service.AuthorityService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("authorizationRequest")
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/search")
	public ModelAndView searchPage(Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("role/search", model);
	}

	@RequestMapping(value = "/query")
	public ModelAndView search(Pageable pageable, @QuerydslPredicate(root = RoleModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<RoleResource> assembler,
			Map<String, Object> model, HttpServletRequest request, Locale locale) {
		Page<RoleResource> results = authorityService.findAllRoles(predicate, pageable);

		if (results.hasContent()) {
			model.put("results", results);
			model.put("query", ControllerUtil.buildQuery(parameters));
			return new ModelAndView("role/results", model);
		}
		model.put("error", "No results found.");
		return new ModelAndView("role/search", model);
	}

	@RequestMapping("/new")
	public ModelAndView add(Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("role/register", model);
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("uid") Long uid, Map<String, Object> model, HttpServletRequest request) {
		Optional<RoleResource> roleById = authorityService.findRoleById(uid);
		if (roleById.isPresent()) {
			model.put("resource", roleById.get());
			return new ModelAndView("role/edit", model);
		}

		model.put("error", "No results found.");
		return new ModelAndView("role/search", model);
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.POST)
	public ModelAndView save(@PathVariable("uid") Long uid, @ModelAttribute("resource") RoleResource resource, BindingResult result,
			Map<String, Object> model, HttpServletRequest request) {
		RoleResource saved = authorityService.updateRole(uid, resource);
		model.put("msg", String.format("Role [%s] saved successfully.", saved.getUid()));
		return new ModelAndView("success", model);
	}
}
