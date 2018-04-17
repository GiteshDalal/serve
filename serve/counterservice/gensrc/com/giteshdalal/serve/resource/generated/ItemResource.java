package com.giteshdalal.serve.resource.generated;

import java.util.*;
import com.giteshdalal.serve.resource.generated.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings(value = { "unused" })
public class ItemResource {

	private Long id;
	private String description;
	private Date created;
	private Date lastModified;
	private List<String> comments;

	// Constructor ==========

	public ItemResource() {
		super();
	}

	// Methods ==============

	public Long getId() {
		return this.id;
	}

  	public void setId(final  Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

  	public void setDescription(final  String description) {
		this.description = description;
	}

	public Date getCreated() {
		return this.created;
	}

  	public void setCreated(final  Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

  	public void setLastModified(final  Date lastModified) {
		this.lastModified = lastModified;
	}

	public List<String> getComments() {
		return this.comments;
	}

  	public void setComments(final  List<String> comments) {
		this.comments = comments;
	}

} 