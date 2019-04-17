package com.giteshdalal.authservice.email.template.impl;

import java.util.Locale;
import javax.mail.MessagingException;

import com.giteshdalal.authservice.email.template.EmailTemplate;
import com.giteshdalal.authservice.resource.UserResource;
import com.giteshdalal.authservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class UserRegisteredEmailTemplate implements EmailTemplate<UserResource> {

	private static String TEMPLATE = "html/email-registered";
	private static final String BACKGROUND_IMAGE = "email/assets/backdrop.png";
	private static final String LOGO_IMAGE = "email/assets/icon.png";
	private static final String PNG_MIME = "image/png";

	private final String from;
	private final String subject;
	private final EmailService emailService;

	@Autowired
	public UserRegisteredEmailTemplate(final EmailService emailService, @Value("${email.from:serve@giteshdalal.com}") final String from,
			@Value("${email.subject.user-registered:Registered Successfully!}") final String subject) {
		this.emailService = emailService;
		this.from = from;
		this.subject = subject;
	}

	@Override
	public void send(final UserResource resource, final Locale locale) throws MessagingException {
		// Create a new message
		MimeMessageHelper message = emailService.newMessage(resource.getEmail(), from, subject, true);

		// Initialize html
		Context context = new Context(locale);
		context.setVariable("username", resource.getUsername());
		emailService.initHtmlMessage(message, TEMPLATE, context);

		// Add inline images
		emailService.addInlineResource(message, "background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
		emailService.addInlineResource(message, "logo", new ClassPathResource(LOGO_IMAGE), PNG_MIME);

		// Send Email
		emailService.send(message);
	}
}
