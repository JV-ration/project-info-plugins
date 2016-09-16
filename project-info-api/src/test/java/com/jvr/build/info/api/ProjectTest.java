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
        Scope scope = project.getDependencies().get(0);
        assertEquals("Scope is lost", "compile", scope.getScope());
        assertEquals("Scope is lost", 1, scope.getDependencies().size());
        assertEquals("dependency is changed", dependency, scope.getDependencies().get(0));

    }

}