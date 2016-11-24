package com.jvr.gradle.plugins.info;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import javax.inject.Inject;

public class ProjectInfoModelPlugin implements Plugin<Project> {

    private final ToolingModelBuilderRegistry registry;

    @Inject
    public ProjectInfoModelPlugin(ToolingModelBuilderRegistry registry) {
        this.registry = registry;
    }

    public void apply(Project project) {
        registry.register(new ProjectInfoModelBuilder());
    }

}
