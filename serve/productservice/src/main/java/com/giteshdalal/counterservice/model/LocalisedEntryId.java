package com.giteshdalal.counterservice.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author gitesh
 *
 */
@SuppressWarnings("serial")
@Embeddable
public class LocalisedEntryId implements Serializable {

	private String localeId;
	private Long ownerId;

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(final String localeId) {
		this.localeId = localeId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(final Long ownerId) {
		this.ownerId = ownerId;
	}

}
