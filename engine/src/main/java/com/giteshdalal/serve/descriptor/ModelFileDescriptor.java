package com.giteshdalal.serve.descriptor;

import java.util.List;

/**
 * @author gitesh
 *
 */
public class ModelFileDescriptor {
	private ConfigurationDescriptor configuration;
	private List<ModelDescriptor> models;

	public ConfigurationDescriptor getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ConfigurationDescriptor configuration) {
		this.configuration = configuration;
	}

	public List<ModelDescriptor> getModels() {
		return models;
	}

	public void setModels(List<ModelDescriptor> models) {
		this.models = models;
	}
}
