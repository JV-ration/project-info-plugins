package com.jvr.gradle.plugins.info

import org.gradle.api.Project
import org.gradle.api.Plugin

class ProjectInfoPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('info', type: ProjectInfoTask)
    }
}
