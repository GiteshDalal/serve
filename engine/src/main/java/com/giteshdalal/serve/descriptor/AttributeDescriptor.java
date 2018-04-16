package com.giteshdalal.serve.descriptor;

import java.util.List;

/**
 * @author gitesh
 *
 */
public class AttributeDescriptor {
	private String name;
	private String type;
	private Boolean includeInResource = Boolean.TRUE;
	private Boolean includeInModel = Boolean.TRUE;
	private List<String> annotations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIncludeInResource() {
		return includeInResource;
	}

	public void setIncludeInResource(Boolean includeInResource) {
		this.includeInResource = includeInResource;
	}

	public Boolean getIncludeInModel() {
		return includeInModel;
	}

	public void setIncludeInModel(Boolean includeInModel) {
		this.includeInModel = includeInModel;
	}

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

}
