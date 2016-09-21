package com.jvr.build.info.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Descriptor of the project, which is being scanned
 */
public class ProjectRoot extends Project {

    private Project parent = null;
    private List<String> modules = null;
    private String name;
    private String description;

    public ProjectRoot(Project project) {
        setGroupId(project.getGroupId());
        setArtifactId(project.getArtifactId());
        setVersion(project.getVersion());
        setType(project.getType());
    }

    public ProjectRoot() {
    }

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }

    public void addModule(String module) {
        if (modules == null) {
            modules = new ArrayList<String>();
        }
        modules.add(module);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
