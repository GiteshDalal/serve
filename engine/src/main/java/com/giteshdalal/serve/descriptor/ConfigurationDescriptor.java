package com.giteshdalal.serve.descriptor;

/**
 * @author gitesh
 *
 */
public class ConfigurationDescriptor {
	private String annutation;
	private String modelPackage;
	private String resourcePackage;
	private String repositoryPackage;

	public String getAnnutation() {
		return annutation;
	}

	public void setAnnutation(String annutation) {
		this.annutation = annutation;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public String getresourcePackage() {
		return resourcePackage;
	}

	public void setresourcePackage(String resourcePackage) {
		this.resourcePackage = resourcePackage;
	}

	public String getRepositoryPackage() {
		return repositoryPackage;
	}

	public void setRepositoryPackage(String repositoryPackage) {
		this.repositoryPackage = repositoryPackage;
	}
}
