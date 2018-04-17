package com.giteshdalal.serve.model.generated;

import java.util.*;
import javax.persistence.*;
import com.giteshdalal.serve.model.*;
import com.giteshdalal.serve.model.generated.*;

/**
 * @author gitesh
 *
 */
@Entity(name="Customer")@SuppressWarnings(value = { "unused" })
public class CustomerModel extends ItemModel {

	public static final String NAME = "Customer";

	private Long pk;
	

	// Constructor ==========

	public CustomerModel() {
		super();
	}

	// Methods ==============
	
	public Long getPk() {
		return this.pk;
	}

  	public void setPk(final Long pk) {
		this.pk = pk;
	}

}