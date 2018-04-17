package com.giteshdalal.counterservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.apache.commons.collections4.IterableUtils;

/**
 * @author gitesh
 *
 */
@Entity(name = "Localised")
@SuppressWarnings("serial")
public class Localised implements Serializable {

	public static final String NAME = "Localised";

	@Id
	@TableGenerator(name = "LOCALISED_GEN", pkColumnValue = "LOCALISED_SEQ", initialValue = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LOCALISED_GEN")
	private Long id;

	@Column
	private String defaultLocale;

	@OneToMany(mappedBy = "localised", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, targetEntity = LocalisedEntry.class)
	private List<LocalisedEntry> entries;

	// Constructors

	public Localised() {
		this.entries = new ArrayList<LocalisedEntry>();
		this.defaultLocale = Locale.ENGLISH.getLanguage();
	}

	public Localised(final Locale defaultLocale, final List<LocalisedEntry> entries) {
		this.entries = entries;
		this.defaultLocale = defaultLocale.getLanguage();
	}

	public Localised(final String defaultLocale, final List<LocalisedEntry> entries) {
		this.entries = entries;
		this.defaultLocale = new Locale(defaultLocale).getLanguage();
	}

	// Methods

	public final Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(final String defaultLocale) {
		this.defaultLocale = new Locale(defaultLocale).getLanguage();
	}

	public void setDefaultLocale(final Locale defaultLocale) {
		this.defaultLocale = defaultLocale.getLanguage();
	}

	public void addString(final Locale locale, final String text) {
		final String localeId = locale.getLanguage();
		Optional<LocalisedEntry> optional = entries.stream().filter(e -> e.getLocale().equals(localeId)).findFirst();
		if (optional.isPresent()) {
			optional.get().setLocale(localeId);
			optional.get().setValue(text);
		} else {
			entries.add(new LocalisedEntry(localeId, text, this));
		}
	}

	public void addString(final String locale, final String text) {
		this.addString(new Locale(locale), text);
	}

	public String getString() {
		return this.getString(this.defaultLocale);
	}

	public String getString(final String locale) {
		return this.getString(new Locale(locale));
	}

	public String getString(final Locale locale) {
		final String lang = locale.getLanguage();
		final LocalisedEntry entry = IterableUtils.find(entries, e -> e.getLocale().equals(lang));
		return entry != null ? entry.getValue() : null;
	}

}
