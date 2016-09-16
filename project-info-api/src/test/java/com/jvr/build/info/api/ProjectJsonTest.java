package com.jvr.build.info.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ProjectJsonTest {

    private final String dependencyArtifactId = "dependency-artifact-id";
    private final String projectArtifactId = "project-artifact-id";
    private final String testScope = "compile";

    @Test
    public void testToString() throws Exception {
        String json = ProjectJson.toString(createdTestProject(), false);
        assertTrue("project is not serialized correctly", json.contains(dependencyArtifactId));
        assertFalse("json should not expose internal structure of the Project", json.contains("dependenciesMap"));
    }

    @Test
    public void testToJson() throws Exception {
        String json = ProjectJson.toString(createdTestProject(), false);

        Project project = ProjectJson.fromJson(json);
        assertEquals(projectArtifactId, project.getArtifactId());
        assertEquals(1, project.getDependencies().size());

        Scope scope = project.getDependencies().get(0);
        assertEquals(testScope, scope.getScope());
        assertEquals(1, scope.getDependencies().size());

        Project dependency = scope.getDependencies().get(0);
        assertEquals(dependencyArtifactId, dependency.getArtifactId());

    }

    private ProjectRoot createdTestProject() {
        ProjectRoot project = new ProjectRoot();
        project.setArtifactId(projectArtifactId);
        Project dependency = new Project();
        dependency.setArtifactId(dependencyArtifactId);
        project.addDependency(testScope, dependency);
        return project;
    }

}