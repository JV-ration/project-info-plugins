package com.jvr.build.info.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds list of dependencies for scope
 */
public class Scope {

    private String scope = null;
    private List<Project> dependencies = new ArrayList<Project>();

    public Scope() {
    }

    public Scope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<Project> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Project> dependencies) {
        this.dependencies = dependencies;
    }

    public void add(Project dependency) {
        dependencies.add(dependency);
    }
}
