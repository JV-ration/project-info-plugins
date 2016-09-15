package com.jvr.build.info.api;

import org.junit.Test;

import static org.junit.Assert.*;


public class ProjectJsonTest {

    @Test
    public void testToString() throws Exception {

        final String dependencyArtifactId = "dependency-artifact-id";

        Project project = new Project();
        project.setArtifactId("project");

        Project dependency = new Project();
        dependency.setArtifactId(dependencyArtifactId);

        project.addDependency("compile", dependency);

        String json = ProjectJson.toString(project, false);

        assertTrue("project is not serialized correctly", json.contains(dependencyArtifactId));
        assertFalse("json should not expose internal structure of the Project", json.contains("dependenciesMap"));

    }

}