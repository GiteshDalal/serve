package com.giteshdalal.serve.resource.generated;

import java.util.*;
import com.giteshdalal.serve.resource.generated.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings(value = { "unused" })
public class CustomerResource extends ItemResource {

	private Long pk;

	// Constructor ==========

	public CustomerResource() {
		super();
	}

	// Methods ==============

	public Long getPk() {
		return this.pk;
	}

  	public void setPk(final  Long pk) {
		this.pk = pk;
	}

} 