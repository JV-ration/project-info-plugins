package com.jvr.gradle.invoker;

import com.jvr.build.info.api.ProjectRoot;
import org.junit.Test;

import java.io.File;

import static com.jvr.TestConstants.LOCAL_REPO_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GradleProjectInfoRetrieverTest {

    @Test
    public void testSample1() {
        GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
        ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("src/test/resources/gradle/sample1"), LOCAL_REPO_PATH);
        assertNotNull("project is not retrieved", projectRoot);
    }

    @Test
    public void testMultiProject() {
        GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
        ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("src/test/resources/gradle/multi"), LOCAL_REPO_PATH);
        assertNotNull("project is not retrieved", projectRoot);
        assertEquals("Count of multi project modules", 3, projectRoot.getModules().size());
    }

}
