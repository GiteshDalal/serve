package com.giteshdalal.serve;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * @author gitesh
 *
 */
public class ServiceGeneratorPlugin implements Plugin<Project> {
	public void apply(Project project) {
		Task serveTask = project.getTasks().create("newservice", GenerateNewServiceTask.class);
		serveTask.setGroup("build");
		serveTask.setDescription("Generates a new serve microservice using 'templateservice' located in '"
				+ project.getPath() + "/assets/'");
	}
}