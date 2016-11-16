package com.jvr.gradle.plugins.info

import org.gradle.api.Plugin
import org.gradle.api.invocation.Gradle

class ProjectInfoPlugin implements Plugin<Gradle> {

    void apply(Gradle gradle) {
        gradle.allprojects{ project ->
            // At this moment projects' configurations are not loaded yet
            // Therefore the task will be called later
            if( project.parent == null ) {
                project.task('info', type: ProjectInfoTask)
            }
        }
    }

}
