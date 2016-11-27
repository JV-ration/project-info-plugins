package com.jvr.gradle.plugins.info;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import javax.inject.Inject;

public class ProjectInfoPlugin implements Plugin<Project> {

    static final String TASK_NAME = "project-info";

    private final ToolingModelBuilderRegistry registry;

    @Inject
    public ProjectInfoPlugin(ToolingModelBuilderRegistry registry) {
        this.registry = registry;
    }

    public void apply(Project project) {

        registry.register(new ProjectInfoModelBuilder());

        if (project.getParent() == null) {
            project.getTasks().create(TASK_NAME, ProjectInfoTask.class);
        }

    }

}
