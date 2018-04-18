package com.giteshdalal.productservice.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 * @author gitesh
 *
 */
@Entity(name = "LocalisedEntry")
@SuppressWarnings("serial")
public class LocalisedEntry implements Serializable {

	public static final String NAME = "LocalisedEntry";

	@EmbeddedId
	private LocalisedEntryId id;

	private String value;

	@MapsId("ownerId")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Localised.class)
	@JoinColumn
	private Localised localised;

	// Constructors

	public LocalisedEntry() {

	}

	public LocalisedEntry(final String locale, final String value, final Localised localised) {
		this.id = new LocalisedEntryId();
		this.id.setLocaleId(locale);
		this.value = value;
		this.localised = localised;
	}

	// Methods

	public LocalisedEntryId getId() {
		return id;
	}

	public void setId(final LocalisedEntryId id) {
		this.id = id;
	}

	public String getLocale() {
		return this.id.getLocaleId();
	}

	public void setLocale(final String locale) {
		this.getId().setLocaleId(locale);
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Localised getLocalised() {
		return localised;
	}

	public void setLocalised(final Localised localised) {
		this.localised = localised;
	}

}
