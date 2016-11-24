package com.jvr.gradle.plugins.info;

import com.jvr.build.info.api.Project;
import com.jvr.build.info.api.ProjectRoot;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableDependency;

import java.util.HashMap;
import java.util.Set;

/**
 * A dependency node visitor that serializes visited nodes to JSON format
 */
class GradleNodeVisitor {

    private ProjectRoot root = null;
    private HashMap<Object, Project> projects = new HashMap<>();

    /**
     * This method is expected to be called for every Gradle configuration.
     * Therefore it keeps reference to root project
     */
    ProjectRoot visit(RenderableDependency node, String configuration) {

        if (root == null) {
            root = new ProjectRoot(toProject(node));
        }

        Set<? extends RenderableDependency> children = node.getChildren();
        for (RenderableDependency child : children) {
            Project dependency = getProject(child);
            root.addDependency(configuration, dependency);
        }

        return root;

    }

    /**
     * Either creates new Project or looks it up in cache
     *
     * @param node to convert to Project
     * @return either created or cached Project
     */
    private Project getProject(RenderableDependency node) {
        if (node == null) {
            return null;
        }
        Project candidate = projects.get(node.getId());
        if (candidate == null) {
            candidate = toProject(node);
            projects.put(node.getId(), candidate);
        }
        return candidate;
    }

    static Project toProject(RenderableDependency node) {
        Project candidate = new Project();
        // need to check what's in the node
        return candidate;
    }

    ProjectRoot getRoot() {
        return root;
    }

}
