package com.giteshdalal.authservice.service;

import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

/**
 * @author gitesh
 */
public interface EmailService {

	void sendSimpleMessage(String to, String subject, String text);

	void sendSimpleMessage(String to, String subject, String text, File attachment) throws MessagingException;

	MimeMessageHelper initTextMessage(MimeMessageHelper message, String template, Context ctx) throws MessagingException;

	MimeMessageHelper initHtmlMessage(MimeMessageHelper message, String template, Context ctx) throws MessagingException;

	MimeMessageHelper initEditableMessage(MimeMessageHelper message, String htmlContent, Context ctx) throws MessagingException;

	MimeMessageHelper newMessage(String to, String from, String subject, boolean multipart) throws MessagingException;

	String getEditableMailTemplate(String templateClassPath) throws IOException;

	void addAttachment(MimeMessageHelper message, File attachment) throws MessagingException;

	void addAttachment(MimeMessageHelper message, String attachmentFileName, byte[] attachmentBytes, String attachmentContentType)
			throws MessagingException;

	void addInlineResource(MimeMessageHelper message, File resource) throws MessagingException;

	void addInlineResource(MimeMessageHelper message, String resourceName, byte[] resourceBytes, String resourceType)
			throws MessagingException;

	void addInlineResource(MimeMessageHelper message, String resourceName, ClassPathResource resourcePath, String resourceType)
			throws MessagingException;

	void send(MimeMessageHelper message);
}
