package com.jvr.gradle.plugins.info;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.invocation.Gradle;

public class ProjectInfoPlugin implements Plugin<Gradle> {

    public void apply(Gradle gradle) {
        // At this moment projects' configurations are not loaded yet
        // Therefore the task will be called later
        gradle.allprojects(new Action<Project>() {
            public void execute(Project project) {
                if( project.getParent() == null ) {
                    project.getTasks().create("info", ProjectInfoTask.class);
                }
            }
        });
    }

}
