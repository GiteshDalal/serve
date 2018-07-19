package com.giteshdalal.serve;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * @author gitesh
 *
 */
public class EnginePlugin implements Plugin<Project> {
	public void apply(Project project) {
		Task serveTask = project.getTasks().create("serve", GenerateFilesTask.class, t -> {
			t.setGensrc(project.file(EnginePluginConstants.GENSRC));
			t.setResources(project.file(EnginePluginConstants.RESOURCES));
			t.setTemplates(project.file(EnginePluginConstants.TEMPLATES));
			t.setYmlSearchRegex(EnginePluginConstants.YML_SEARCH_REGEX);
		});
		serveTask.setGroup("build");
		serveTask.setDescription(
				"Generates models, respositories and resources in gensrc folder using *models.yml files");
	}
}