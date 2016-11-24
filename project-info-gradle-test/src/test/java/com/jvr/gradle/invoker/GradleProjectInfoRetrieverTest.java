package com.jvr.gradle.invoker;

import com.jvr.build.info.api.ProjectRoot;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GradleProjectInfoRetrieverTest {

    private static final String INIT_SCRIPT = "../custom-model-plugin.gradle";

    @Test
    public void testSample1() {
        GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
        ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("src/test/resources/sample1"), INIT_SCRIPT);
        assertNotNull("project is not retrieved", projectRoot);
    }

    @Test
    public void testMultiProject() {
        GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
        ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("src/test/resources/multi"), INIT_SCRIPT);
        assertNotNull("project is not retrieved", projectRoot);
        assertEquals("Count of multi project modules", 3, projectRoot.getModules().size());
    }

}
