package com.giteshdalal.emailservice.template;

import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;

/**
 * @param <R>
 * 		Resource
 * @author gitesh
 */
public interface EmailTemplate {

	/**
	 * Sends an email
	 *
	 * @param resourceMap
	 * @param locale
	 * @throws MessagingException
	 */
	void send(Map<String, Object> resourceMap, Locale locale) throws MessagingException;
}