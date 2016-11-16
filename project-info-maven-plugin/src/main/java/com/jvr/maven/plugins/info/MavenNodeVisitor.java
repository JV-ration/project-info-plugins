package com.jvr.maven.plugins.info;

import com.jvr.build.info.api.Project;
import com.jvr.build.info.api.ProjectRoot;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.DependencyNodeVisitor;

import java.util.HashMap;
import java.util.List;

/**
 * A dependency node visitor that serializes visited nodes to JSON format
 */
class MavenNodeVisitor implements DependencyNodeVisitor {

    private ProjectRoot root = null;
    private HashMap<String, Project> projects = new HashMap<String, Project>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean visit(DependencyNode node) {

        Project project = toProject(node);

        if (node.getParent() == null || node.getParent() == node) {
            root = new ProjectRoot(project);
            project = root;
        }

        List<DependencyNode> children = node.getChildren();
        for (DependencyNode child : children) {
            Project dependency = toProject(child);
            project.addDependency(child.getArtifact().getScope(), dependency);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean endVisit(DependencyNode node) {
        // visit next sibling
        return true;
    }

    /**
     * Either creates new Project or looks it up in cache
     *
     * @param node to convert to Project
     * @return either created or cached Project
     */
    private Project toProject(DependencyNode node) {
        if (node == null) {
            return null;
        }
        Project candidate = projects.get(node.toNodeString());
        if (candidate == null) {
            Artifact artifact = node.getArtifact();
            candidate = toProject(artifact);
            projects.put(node.toNodeString(), candidate);
        }
        return candidate;
    }

    static Project toProject(Artifact artifact) {
        Project candidate = new Project();
        candidate.setGroupId(artifact.getGroupId());
        candidate.setArtifactId(artifact.getArtifactId());
        candidate.setGroupId(artifact.getGroupId());
        candidate.setVersion(artifact.getVersion());
        candidate.setType(artifact.getType());
        return candidate;
    }

    ProjectRoot getRoot() {
        return root;
    }
}
