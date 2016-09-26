package com.jvr.build.info.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProjectTest {

    @Test
    public void testAddDependency() {

        Project project = new Project();
        project.setArtifactId("project");
        Project dependency = new Project();
        dependency.setArtifactId("dependency");
        project.addDependency("compile", dependency);

        assertEquals("dependency is not added", 1, project.getDependencies().size());
        Dependency dependency1 = project.getDependencies().get(0);
        assertEquals("Scope is lost", "compile", dependency1.getScope());
        assertEquals("dependency is changed", dependency, dependency1.getDependency());

    }

}