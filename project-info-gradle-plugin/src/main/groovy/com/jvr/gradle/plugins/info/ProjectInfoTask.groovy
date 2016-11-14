package com.jvr.gradle.plugins.info

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ProjectInfoTask extends DefaultTask {

    String greeting = 'hello from Project Info Plugin !!!'

    ProjectInfoTask() {
        description = 'Generate unified information about the projects. See https://github.com/JV-ration/project-info-plugins'
    }

    @TaskAction
    def greet() {
        println greeting
    }
}
