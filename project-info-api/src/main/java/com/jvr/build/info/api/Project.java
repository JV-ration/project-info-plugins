package com.jvr.build.info.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Either root or dependency or module (sub-project)
 */
public class Project {

    private String groupId = null;
    private String artifactId = null;
    private String version = null;
    private String type = null;

    private Project parent = null;

    // exclude from JSON serialization
    transient private HashMap<String, Scope> dependenciesMap = null;

    private List<Scope> dependencies = null;
    private List<String> modules = null;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }

    /**
     * Do not add elements to the returned list
     *
     * @return unmodifiable list of dependencies
     */
    public List<Scope> getDependencies() {
        return Collections.unmodifiableList(dependencies);
    }

    public void setDependencies(List<Scope> dependencies) {
        this.dependencies = dependencies;
        if (dependencies != null) {
            dependenciesMap = new HashMap<String, Scope>(dependencies.size());
            for (Scope scope : dependencies) {
                dependenciesMap.put(scope.getScope(), scope);
            }
        }
    }

    public void addDependency(String scope, Project dependency) {

        if (dependencies == null) {
            dependencies = new ArrayList<Scope>();
            dependenciesMap = new HashMap<String, Scope>();
        }

        if (dependenciesMap.containsKey(scope)) {
            dependenciesMap.get(scope).add(dependency);
        } else {
            Scope newScope = new Scope(scope);
            newScope.add(dependency);
            dependenciesMap.put(scope, newScope);
            dependencies.add(newScope);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!groupId.equals(project.groupId)) return false;
        if (!artifactId.equals(project.artifactId)) return false;
        if (version != null ? !version.equals(project.version) : project.version != null) return false;
        return type != null ? type.equals(project.type) : project.type == null;

    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + artifactId.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
