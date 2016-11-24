package com.jvr.gradle.plugins.info;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.invocation.Gradle;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import javax.inject.Inject;

public class ProjectInfoPlugin implements Plugin<Gradle> {

    public static final String TASK_NAME = "project-info";

    public void apply(Gradle gradle) {

        // At this moment projects' configurations are not loaded yet
        // Therefore the task will be called later
        gradle.allprojects(new Action<Project>() {
            public void execute(Project project) {
                if( project.getParent() == null ) {
                    project.getTasks().create(TASK_NAME, ProjectInfoTask.class);
                }
            }
        });

    }

}
