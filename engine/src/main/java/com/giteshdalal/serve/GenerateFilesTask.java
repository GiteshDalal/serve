package com.giteshdalal.serve;

import com.giteshdalal.serve.descriptor.ModelFileDescriptor;
import com.giteshdalal.serve.generator.ModelGenerator;
import com.giteshdalal.serve.generator.RepositoryGenerator;
import com.giteshdalal.serve.generator.ResourceGenerator;
import com.giteshdalal.serve.util.EnginePluginUtil;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GenerateFilesTask extends DefaultTask {

    private File gensrc;

    private File resources;

    private File templates;

    private String ymlSearchRegex;

    private File getGensrc() {
        return gensrc;
    }

    @Input
    public void setGensrc(File gensrc) {
        this.gensrc = gensrc;
    }

    private File getResources() {
        return resources;
    }

    @Input
    public void setResources(File resources) {
        this.resources = resources;
    }

    private File getTemplates() {
        return templates;
    }

    @Input
    public void setTemplates(File templates) {
        this.templates = templates;
    }

    private String getYmlSearchRegex() {
        return ymlSearchRegex;
    }


    @Input
    public void setYmlSearchRegex(String ymlSearchRegex) {
        this.ymlSearchRegex = ymlSearchRegex;
    }

    @TaskAction
    public void generateFiles() {
        //Step 1. Clean gensrc folder, located in project base directory

        try {
            if (this.getGensrc().exists()) {
                EnginePluginUtil.emptyDirectory(this.getGensrc());
            } else {
                System.out.println("[SERVE][INIT] gensrc folder doesn't exist...");
            }
        } catch (IOException e) {
            throw new GradleException("[SERVE][ERROR] Unable to clean: " + this.getGensrc().getPath(), e);
        }
        System.out.println(EnginePluginConstants.SIGNATURE);

        //Step 2. Generate Files

        final List<File> inputFiles = EnginePluginUtil.findInputFilesIn(this.getResources(), this.getYmlSearchRegex());
        if (inputFiles.isEmpty()) {
            throw new GradleException("[SERVE][ERROR] No files found using regex: " + this.getYmlSearchRegex());
        }
        final List<ModelFileDescriptor> modelFileList = EnginePluginUtil.translateFiles(inputFiles);
        this.generateFilesFor(modelFileList);
    }

    private void generateFilesFor(final List<ModelFileDescriptor> modelFileList) {
        System.out.println("[SERVE][INIT] Generating files in : " + this.getGensrc().getPath());
        int counter = 0;

        //Step 2.1 Generate MODEL Files
        final ModelGenerator modelGenerator = new ModelGenerator();
        for (ModelFileDescriptor fileDescriptor : modelFileList) {
            counter += modelGenerator.generateFiles(fileDescriptor, this.getGensrc().getAbsolutePath(), this.getTemplates().getAbsolutePath());
        }

        //Step 2.2 Generate RESOURCE Files
        final ResourceGenerator resourceGenerator = new ResourceGenerator();
        for (ModelFileDescriptor fileDescriptor : modelFileList) {
            counter += resourceGenerator.generateFiles(fileDescriptor, this.getGensrc().getAbsolutePath(), this.getTemplates().getAbsolutePath());
        }

        //Step 2.3 Generate REPOSITORY Files
        final RepositoryGenerator repositoryGenerator = new RepositoryGenerator();
        for (ModelFileDescriptor fileDescriptor : modelFileList) {
            counter += repositoryGenerator.generateFiles(fileDescriptor, this.getGensrc().getAbsolutePath(), this.getTemplates().getAbsolutePath());
        }

        System.out.println("[SERVE][INIT] Number of files generated : " + counter);
    }
}
