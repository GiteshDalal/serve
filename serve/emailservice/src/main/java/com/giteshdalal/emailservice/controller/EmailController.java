package com.giteshdalal.emailservice.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.mail.MessagingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteshdalal.emailservice.ContextProvider;
import com.giteshdalal.emailservice.exception.BadRequestEmailServiceException;
import com.giteshdalal.emailservice.template.EmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitesh
 */
@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

	@Autowired
	private ObjectMapper mapper;


	@GetMapping(value = "/{bean}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity testAPI(@PathVariable("bean") String bean, HttpEntity<String> httpEntity, Locale locale) {

		return ResponseEntity.ok(bean);
	}

	@PostMapping(value = "/{bean}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
	public ResponseEntity triggerEmail(@PathVariable("bean") String bean, HttpEntity<String> httpEntity, Locale locale) {
		if (log.isDebugEnabled()) {
			log.debug("Sending email using template bean : " + bean);
			log.debug("Email Context : " + httpEntity.getBody());
		}
		try {
			Object emailBean = ContextProvider.getBean(bean);
			if (!(emailBean instanceof EmailTemplate)) {
				throw new BadRequestEmailServiceException("Unable to find email template : " + bean);
			}
			EmailTemplate emailTemplate = (EmailTemplate) emailBean;
			JsonNode jsonNode = mapper.readTree(httpEntity.getBody());
			Map<String, Object> contextResource = mapper.convertValue(jsonNode, Map.class);
			CompletableFuture.runAsync(() -> {
				try {
					emailTemplate.send(contextResource, locale);
				} catch (MessagingException e) {
					e.printStackTrace();
					log.error("Unable to send email : " + bean, e);
				}
			});
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.noContent().build();
	}
}

