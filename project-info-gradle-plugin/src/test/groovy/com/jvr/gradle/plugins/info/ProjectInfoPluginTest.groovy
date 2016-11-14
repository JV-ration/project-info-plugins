package com.jvr.gradle.plugins.info

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class ProjectInfoPluginTest {
    @Test
    public void greeterPluginAddsGreetingTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.jvr.project-info'

        assertTrue(project.tasks.info instanceof ProjectInfoTask)
    }
}
