package com.giteshdalal.serve.descriptor;

/**
 * @author gitesh
 *
 */
public class ServiceDescriptor {
	private String name;
	private String group;
	private String port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}