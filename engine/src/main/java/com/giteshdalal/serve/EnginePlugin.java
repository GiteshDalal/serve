package com.giteshdalal.serve;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class EnginePlugin implements Plugin<Project> {
    public void apply(Project project) {
        EnginePluginExtension extension = project.getExtensions().create("engine", EnginePluginExtension.class, project);
        project.getTasks().create("generateFiles", GenerateFilesTask.class, t -> {
            t.setGensrc(project.file(EnginePluginConstants.GENSRC));
            t.setResources(project.file(EnginePluginConstants.RESOURCES));
            t.setTemplates(project.file(extension.getTemplateFolder()));
            t.setYmlSearchRegex(extension.getYmlSearchRegex());
        });
    }
}