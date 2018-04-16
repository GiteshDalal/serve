package com.giteshdalal.serve;

public class EnginePluginExtension {

    private String templateFolder;

    private String ymlSearchRegex;

    public String getTemplateFolder() {
        return templateFolder;
    }

    public void setTemplateFolder(String templateFolder) {
        this.templateFolder = templateFolder;
    }

    public String getYmlSearchRegex() {
        return ymlSearchRegex;
    }

    public void setYmlSearchRegex(String ymlSearchRegex) {
        this.ymlSearchRegex = ymlSearchRegex;
    }
}
