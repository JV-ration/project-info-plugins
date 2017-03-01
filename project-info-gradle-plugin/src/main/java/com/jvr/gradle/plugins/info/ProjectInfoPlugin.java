package com.jvr.gradle.plugins.info;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import javax.inject.Inject;

import static com.jvr.build.info.api.Constants.TASK_NAME;

public class ProjectInfoPlugin implements Plugin<Project> {

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
