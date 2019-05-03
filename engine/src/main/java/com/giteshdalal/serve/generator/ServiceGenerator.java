package com.giteshdalal.serve.generator;

import java.io.File;
import java.io.IOException;

import com.giteshdalal.serve.EnginePluginConstants;
import com.giteshdalal.serve.descriptor.ServiceDescriptor;
import com.giteshdalal.serve.util.EnginePluginUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.gradle.api.GradleException;
import org.gradle.api.Project;

/**
 * @author gitesh
 */
public class ServiceGenerator {

	public void generate(ServiceDescriptor service, Project project) {
		final File serviceDirectory = new File(service.getName().toLowerCase() + "service");
		if (!serviceDirectory.exists()) {
			final File resourceLoaderDirectory = project.file(EnginePluginConstants.SERVICE_GRADLE_VM);
			Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, resourceLoaderDirectory.getAbsolutePath());
			Velocity.init();

			final VelocityContext context = new VelocityContext();
			context.put("service", service);
			context.put("StringUtils", StringUtils.class);

			this.generateResourceFiles(service, project, context);
			this.generateJavaFiles(service, project, context);

			// copy template files to resources
			this.copyTemplateFiles(service, project);
			this.generateGradleAndDockerFiles(service, project, context);

			// add service in settings.gradle file
			final String text = "\ninclude '" + service.getName().toLowerCase() + "service'";
			EnginePluginUtil.appendToFile(project.file("settings.gradle"), text);

		} else {
			throw new GradleException("[SERVE][ERROR] service with name '" + service.getName() + "' already exists");
		}
	}

	private void copyTemplateFiles(ServiceDescriptor service, Project project) {
		File templateDir = project.file(EnginePluginConstants.SERVICE_COPY_TEMPLATES);
		File copyToDir = project
				.file(service.getName().toLowerCase() + "service" + File.separator + EnginePluginConstants.TEMPLATES);
		try {
			FileUtils.copyDirectory(templateDir, copyToDir);
		} catch (IOException e) {
			throw new IllegalStateException(
					"Couldn't copy templates \nfrom: " + templateDir.getPath() + "\n to: " + copyToDir.getPath(), e);
		}
	}

	private void generateGradleAndDockerFiles(ServiceDescriptor service, Project project, VelocityContext context) {

		// generate build.gradle
		final File buildFile = EnginePluginUtil.createNewFile("build", ".gradle", service.getName().toLowerCase() + "service", ".");
		final String build = "build.gradle";
		EnginePluginUtil.writeFile(build, context, buildFile);

		// generate Dockerfile
		final File dockerFile = EnginePluginUtil.createNewFile("Dockerfile", "", service.getName().toLowerCase() + "service", ".");
		final String docker = "Dockerfile";
		EnginePluginUtil.writeFile(docker, context, dockerFile);

		// append to docker-compose.yml
		final File dockerComposeFile = EnginePluginUtil.createNewFile("docker-compose", ".yml", "", ".");
		final String dockerCompose = "docker-compose.yml";
		EnginePluginUtil.writeFile(dockerCompose, context, dockerComposeFile);
	}

	private void generateResourceFiles(ServiceDescriptor service, Project project, VelocityContext context) {
		final String path = service.getName().toLowerCase() + "service" + File.separator + EnginePluginConstants.RESOURCES;
		final File resourceLoaderDirectory = project.file(EnginePluginConstants.SERVICE_RESOURCES_VM);
		final String templateParentPath = "resources" + File.separator;

		EnginePluginUtil.writeFiles(resourceLoaderDirectory, templateParentPath, context, path, StringUtils.EMPTY,
				EnginePluginConstants.SERVICE_REGEX, StringUtils.capitalize(service.getName()));
	}

	private void generateJavaFiles(ServiceDescriptor service, Project project, VelocityContext context) {
		final String path = service.getName().toLowerCase() + "service" + File.separator + EnginePluginConstants.JAVA;
		final File resourceLoaderDirectory = project.file(EnginePluginConstants.SERVICE_JAVA_VM);
		final String templateParentPath = "java" + File.separator;

		EnginePluginUtil.writeFiles(resourceLoaderDirectory, templateParentPath, context, path, service.getGroup().toLowerCase(),
				EnginePluginConstants.SERVICE_REGEX, StringUtils.capitalize(service.getName()));
	}

}
