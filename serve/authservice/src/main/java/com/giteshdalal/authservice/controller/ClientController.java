package com.giteshdalal.authservice.controller;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.resource.ClientResource;
import com.giteshdalal.authservice.service.ClientService;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping("/search")
	public ModelAndView searchPage(Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("client/search", model);
	}

	@RequestMapping(value = "/query")
	public ModelAndView search(Pageable pageable, @QuerydslPredicate(root = ClientModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<ClientResource> assembler,
			Map<String, Object> model, HttpServletRequest request, Locale locale) {
		Page<ClientResource> results = clientService.findAllClients(predicate, pageable);
		model.put("clientId", parameters.getFirst("clientId"));
		model.put("email", parameters.getFirst("email"));

		if (results.hasContent()) {
			model.put("results", results);
			return new ModelAndView("client/results", model);
		}
		model.put("error", "No results found.");
		return new ModelAndView("client/search", model);
	}

	@RequestMapping("/new")
	public ModelAndView add(Map<String, Object> model, HttpServletRequest request) {

		return new ModelAndView("client/register", model);
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("uid") Long uid, Map<String, Object> model, HttpServletRequest request) {
		Optional<ClientResource> clientById = clientService.findClientById(uid);
		if (clientById.isPresent()) {
			model.put("resource", clientById.get());
			return new ModelAndView("client/edit", model);
		}

		model.put("error", "No results found.");
		return new ModelAndView("client/search", model);
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.POST)
	public ModelAndView save(@PathVariable("uid") Long uid, @ModelAttribute("resource") ClientResource resource, BindingResult result,
			Map<String, Object> model, HttpServletRequest request) {
			ClientResource saved = clientService.updateClient(uid, resource);
		if (StringUtils.isNotBlank(resource.getClientSecret())) {
			clientService.updateClientSecret(saved.getClientId(), resource.getClientSecret());
			model.put("msg", String.format("Client [%s] saved successfully with new secret.", saved.getClientId()));
		} else {
			model.put("msg", String.format("Client [%s] saved successfully.", saved.getClientId()));
		}
		return new ModelAndView("success", model);
	}
}
