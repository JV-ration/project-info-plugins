package com.jvr.gradle.invoker;

import com.jvr.build.info.api.ProjectRoot;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GradleProjectInfoRetrieverTest {

    private static final String LOCAL_REPO_PATH = "../repo";

    @Test
    public void testSample1() {
        GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
        ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("src/test/resources/sample1"), LOCAL_REPO_PATH);
        assertNotNull("project is not retrieved", projectRoot);
    }

    @Test
    public void testMultiProject() {
        GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
        retriever.setDebugMode(true);
        ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("src/test/resources/multi"), LOCAL_REPO_PATH);
        assertNotNull("project is not retrieved", projectRoot);
        assertEquals("Count of multi project modules", 3, projectRoot.getModules().size());
    }

}
