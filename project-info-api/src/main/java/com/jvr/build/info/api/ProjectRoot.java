package com.jvr.build.info.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Descriptor of the project, which is being scanned
 */
public class ProjectRoot extends Project {

    private Project parent = null;
    private File parentFile = null;
    private List<String> modules = null;

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

    public File getParentFile() {
        return parentFile;
    }

    public void setParentFile(File parentFile) {
        this.parentFile = parentFile;
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

}
