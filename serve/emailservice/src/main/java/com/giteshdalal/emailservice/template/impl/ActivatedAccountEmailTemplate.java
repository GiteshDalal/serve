package com.giteshdalal.emailservice.template.impl;

import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;

import com.giteshdalal.emailservice.service.EmailService;
import com.giteshdalal.emailservice.template.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component("activated-account")
public class ActivatedAccountEmailTemplate implements EmailTemplate {

	private static String TEMPLATE = "html/activated-account";
	private static final String BACKGROUND_IMAGE = "email/assets/backdrop.png";
	private static final String LOGO_IMAGE = "email/assets/icon.png";
	private static final String PNG_MIME = "image/png";

	private final String from;
	private final String subject;
	private final EmailService emailService;

	@Autowired
	public ActivatedAccountEmailTemplate(final EmailService emailService,
			@Value("${email.from:serve@giteshdalal.com}") final String from,
			@Value("${email.subject.activated-account:Account Activated}") final String subject) {
		this.emailService = emailService;
		this.from = from;
		this.subject = subject;
	}

	@Override
	public void send(final Map<String, Object> resourceMap, final Locale locale) throws MessagingException {
		final String email = resourceMap.get("email").toString();

		// Create a new message
		MimeMessageHelper message = emailService.newMessage(email, from, subject, true);

		// Initialize html
		Context context = new Context(locale);
		context.setVariable("resource", resourceMap);
		emailService.initHtmlMessage(message, TEMPLATE, context);

		// Add inline images
		emailService.addInlineResource(message, "background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
		emailService.addInlineResource(message, "logo", new ClassPathResource(LOGO_IMAGE), PNG_MIME);

		// Send Email
		emailService.send(message);
	}
}
