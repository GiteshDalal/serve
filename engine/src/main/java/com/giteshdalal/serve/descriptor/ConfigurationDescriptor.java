package com.giteshdalal.serve.descriptor;

import com.giteshdalal.serve.EnginePluginConstants;

/**
 * @author gitesh
 *
 */
public class ConfigurationDescriptor {
	private String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getModelPackage() {
		return this.group + EnginePluginConstants.MODEL_GENERATED;
	}

	public String getRepositoryPackage() {
		return this.group + EnginePluginConstants.REPOSITORY_GENERATED;
	}

	public String getResourcePackage() {
		return this.group + EnginePluginConstants.RESOURCE_GENERATED;
	}
}
