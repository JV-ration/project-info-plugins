package com.jvr.build.info.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Either root or dependency or module (sub-project)
 */
public class Project implements Serializable {

    private String groupId = null;
    private String artifactId = null;
    private String version = null;
    private String type = null;

    private List<Dependency> dependencies = null;

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

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(String scope, Project dependency) {
        if (dependencies == null) {
            dependencies = new ArrayList<Dependency>();
        }
        dependencies.add(new Dependency(scope, dependency));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (groupId != null ? !groupId.equals(project.groupId) : project.groupId != null) return false;
        if (artifactId != null ? !artifactId.equals(project.artifactId) : project.artifactId != null) return false;
        if (version != null ? !version.equals(project.version) : project.version != null) return false;
        return type != null ? type.equals(project.type) : project.type == null;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
