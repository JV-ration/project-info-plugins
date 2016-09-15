package com.jvr.maven.plugins.info;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class InfoMojoTest {

    private static final String FIXED_POMS_DIR = "src/test/resources/poms";
    private static final String INFO_GOAL = "com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.1-SNAPSHOT:info";
    private static final List<String> GOALS = Arrays.asList(INFO_GOAL, "-q");

    private Invoker invoker = null;

    private Properties buildProperties = new Properties();

    @Before
    public void before() {
        invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/work/tools/apache-maven-3.3.9"));
    }

    @Test
    public void testMinimal() throws Exception {

        InvocationRequest request = prepareRequest("minimal.json");
        request.setBaseDirectory(new File(FIXED_POMS_DIR));
        request.setPomFileName("minimal.xml");

        InvocationResult result = invoker.execute(request);
        assertEquals("Invocation of maven failed", 0, result.getExitCode());

    }

    @Test
    public void testWrong() throws Exception {

        InvocationRequest request = prepareRequest("wrong.json");
        request.setBaseDirectory(new File(FIXED_POMS_DIR));
        request.setPomFileName("wrong.xml");

        InvocationResult result = invoker.execute(request);
        // TODO: details about the errors are seen only as [ERROR] logs in output handler
        assertFalse("Invocation of maven failed", 0 == result.getExitCode());

    }

    @Test
    public void testMavenPlugin() throws Exception {
        InvocationResult result = invoker.execute(prepareRequest("maven-plugin.json"));
        assertEquals("Invocation of maven failed", 0, result.getExitCode());
    }

    @Test
    public void testParent() throws Exception {
        InvocationRequest request = prepareRequest("parent.json").setBaseDirectory(new File(".."));
        InvocationResult result = invoker.execute(request);
        assertEquals("Invocation of maven failed", 0, result.getExitCode());
    }

    private InvocationRequest prepareRequest(String filename) {
        InvocationRequest request = new DefaultInvocationRequest();
        buildProperties.setProperty("outputFile", "target/" + filename);
        return request.setGoals(GOALS).setProperties(buildProperties).setInteractive(false);
    }

}