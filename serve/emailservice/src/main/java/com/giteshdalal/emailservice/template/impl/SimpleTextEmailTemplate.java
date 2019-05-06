package com.giteshdalal.emailservice.template.impl;

import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;

import com.giteshdalal.emailservice.service.EmailService;
import com.giteshdalal.emailservice.template.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component("simple-message")
public class SimpleTextEmailTemplate implements EmailTemplate {

	private static String TEMPLATE = "text/email-message";

	private final String from;
	private final EmailService emailService;

	@Autowired
	public SimpleTextEmailTemplate(final EmailService emailService, @Value("${email.from:serve@giteshdalal.com}") final String from) {
		this.emailService = emailService;
		this.from = from;
	}

	@Override
	public void send(final Map<String, Object> resourceMap, final Locale locale) throws MessagingException {
		final String email = resourceMap.get("email").toString();
		final String subject = resourceMap.get("subject").toString();
		final String title = resourceMap.get("title").toString();
		final String msg = resourceMap.get("message").toString();

		// Create a new message
		MimeMessageHelper message = emailService.newMessage(email, from, subject, false);

		// Initialize html
		Context context = new Context(locale);
		context.setVariable("title", title);
		context.setVariable("message", msg);
		emailService.initTextMessage(message, TEMPLATE, context);

		// Send Email
		emailService.send(message);
	}
}
