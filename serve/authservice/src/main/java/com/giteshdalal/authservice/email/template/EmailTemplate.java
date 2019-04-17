package com.giteshdalal.authservice.email.template;

import java.util.Locale;
import javax.mail.MessagingException;

/**
 * @param <R>
 * 		Resource
 * @author gitesh
 */
public interface EmailTemplate<R> {

	/**
	 * Sends an email
	 *
	 * @param resource
	 * @param locale
	 * @throws MessagingException
	 */
	void send(R resource, Locale locale) throws MessagingException;
}