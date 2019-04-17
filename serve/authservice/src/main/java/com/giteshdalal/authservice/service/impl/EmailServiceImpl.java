package com.giteshdalal.authservice.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.giteshdalal.authservice.ApplicationConfiguration;
import com.giteshdalal.authservice.service.EmailService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author gitesh
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private TemplateEngine emailTemplateEngine;

	@Override
	public void sendSimpleMessage(final String to, final String subject, final String text) {
		Objects.requireNonNull(to, "Email To must not be null");
		Objects.requireNonNull(subject, "Email subject must not be null");
		Objects.requireNonNull(text, "Email text must not be null");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	@Override
	public void sendSimpleMessage(final String to, final String subject, final String text, final File attachment)
			throws MessagingException {
		Objects.requireNonNull(to, "Email To must not be null");
		Objects.requireNonNull(subject, "Email subject must not be null");
		Objects.requireNonNull(text, "Email text must not be null");
		Objects.requireNonNull(attachment, "Attachment must not be null");

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(attachment);
		helper.addAttachment(file.getFilename(), file);

		emailSender.send(message);
	}

	@Override
	public MimeMessageHelper initTextMessage(final MimeMessageHelper message, final String template, final Context ctx)
			throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(template, "Template must not be null");
		Objects.requireNonNull(ctx, "Context must not be null");

		// Create the plain TEXT body using Thymeleaf
		final String textContent = this.emailTemplateEngine.process(template, ctx);
		message.setText(textContent);

		return message;
	}

	@Override
	public MimeMessageHelper initHtmlMessage(final MimeMessageHelper message, final String template, final Context ctx)
			throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(template, "Template must not be null");
		Objects.requireNonNull(ctx, "Context must not be null");

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.emailTemplateEngine.process(template, ctx);
		message.setText(htmlContent, true);

		return message;
	}

	@Override
	public MimeMessageHelper initEditableMessage(final MimeMessageHelper message, final String htmlContent, final Context ctx)
			throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(htmlContent, "htmlContent must not be null");
		Objects.requireNonNull(ctx, "Context must not be null");

		// Create the HTML body using Thymeleaf
		final String output = this.emailTemplateEngine.process(htmlContent, ctx);
		message.setText(output, true);

		return message;
	}

	@Override
	public MimeMessageHelper newMessage(final String to, final String from, final String subject, final boolean multipart)
			throws MessagingException {
		Objects.requireNonNull(to, "Email To must not be null");
		Objects.requireNonNull(from, "Email From must not be null");
		Objects.requireNonNull(subject, "Email subject must not be null");

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "UTF-8");
		helper.setTo(to);
		helper.setFrom(from);
		helper.setSubject(subject);

		return helper;
	}

	@Override
	public String getEditableMailTemplate(String templateClassPath) throws IOException {
		final Resource templateResource = this.applicationContext.getResource(templateClassPath);
		final InputStream inputStream = templateResource.getInputStream();
		return IOUtils.toString(inputStream, ApplicationConfiguration.EMAIL_TEMPLATE_ENCODING);
	}

	@Override
	public void addAttachment(final MimeMessageHelper message, final File attachment) throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(attachment, "Attachment must not be null");

		FileSystemResource file = new FileSystemResource(attachment);
		message.addAttachment(file.getFilename(), file);
	}

	@Override
	public void addAttachment(final MimeMessageHelper message, final String attachmentFileName, final byte[] attachmentBytes,
			final String attachmentContentType) throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(attachmentFileName, "Attachment file name must not be null");
		Objects.requireNonNull(attachmentBytes, "Attachment bytes must not be null");
		Objects.requireNonNull(attachmentContentType, "Attachment content type must not be null");

		final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
		message.addAttachment(attachmentFileName, attachmentSource, attachmentContentType);
	}

	@Override
	public void addInlineResource(final MimeMessageHelper message, final File resource) throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(resource, "Resource must not be null");

		FileSystemResource file = new FileSystemResource(resource);
		message.addInline(file.getFilename(), file);
	}

	@Override
	public void addInlineResource(final MimeMessageHelper message, final String resourceName, final byte[] resourceBytes,
			final String resourceType) throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(resourceName, "Resource name must not be null");
		Objects.requireNonNull(resourceBytes, "Resource bytes must not be null");
		Objects.requireNonNull(resourceType, "Resource type must not be null");

		final InputStreamSource source = new ByteArrayResource(resourceBytes);
		message.addInline(resourceName, source, resourceType);
	}

	@Override
	public void addInlineResource(final MimeMessageHelper message, final String resourceName, final ClassPathResource resourcePath,
			final String resourceType) throws MessagingException {
		Objects.requireNonNull(message, "Message must not be null");
		Objects.requireNonNull(resourceName, "Resource name must not be null");
		Objects.requireNonNull(resourcePath, "Resource path must not be null");
		Objects.requireNonNull(resourceType, "Resource type must not be null");

		message.addInline(resourceName, resourcePath, resourceType);
	}

	@Override
	public void send(final MimeMessageHelper message) {
		Objects.requireNonNull(message, "Message must not be null");

		emailSender.send(message.getMimeMessage());
	}
}
