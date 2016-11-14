package com.jvr.gradle.plugins.info

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class ProjectInfoTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('info', type: ProjectInfoTask)
        assertTrue(task instanceof ProjectInfoTask)
    }
}
