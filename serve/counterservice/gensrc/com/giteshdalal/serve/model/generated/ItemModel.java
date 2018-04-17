package com.giteshdalal.serve.model.generated;

import java.util.*;
import javax.persistence.*;
import com.giteshdalal.serve.model.*;
import com.giteshdalal.serve.model.generated.*;

/**
 * @author gitesh
 *
 */
@MappedSuperclass
@SuppressWarnings(value = { "unused" })
public class ItemModel {

	public static final String NAME = "Item";

	@Id
	@TableGenerator(name="ITEM_GEN", pkColumnValue="ITEM_SEQ", initialValue=100000)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="ITEM_GEN")
	private Long id;
	
	@OneToOne
	@JoinColumn
	private Localised description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;
	
	@ElementCollection
	private List<String> comments;
	

	// Constructor ==========

	public ItemModel() {
		super();
	}

	// Methods ==============
	
	public Long getId() {
		return this.id;
	}

  	public void setId(final Long id) {
		this.id = id;
	}

	public Localised getDescription() {
		return this.description;
	}

  	public void setDescription(final Localised description) {
		this.description = description;
	}

	public Date getCreated() {
		return this.created;
	}

  	public void setCreated(final Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

  	public void setLastModified(final Date lastModified) {
		this.lastModified = lastModified;
	}

	public List<String> getComments() {
		return this.comments;
	}

  	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

}