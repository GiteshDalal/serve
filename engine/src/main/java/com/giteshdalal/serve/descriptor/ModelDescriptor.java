package com.giteshdalal.serve.descriptor;

import java.util.List;

public class ModelDescriptor {
	private String name;
	private String idType = "Long";
	private Boolean hasResource = Boolean.TRUE;
	private Boolean hasRepo = Boolean.TRUE;
	private String parent;
	private List<AttributeDescriptor> attributes;
	private List<String> annotations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public Boolean getHasResource() {
		return hasResource;
	}

	public void setHasResource(Boolean hasResource) {
		this.hasResource = hasResource;
	}

	public Boolean getHasRepo() {
		return hasRepo;
	}

	public void setHasRepo(Boolean hasRepo) {
		this.hasRepo = hasRepo;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<AttributeDescriptor> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeDescriptor> attributes) {
		this.attributes = attributes;
	}

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

}
