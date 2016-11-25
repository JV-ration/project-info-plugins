package com.jvr.gradle.invoker;

import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;
import com.jvr.gradle.model.ProjectInfoModel;
import com.jvr.util.ResourceLoaderUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.gradle.api.GradleException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ModelBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GradleProjectInfoRetriever {

    private static final String REPLACE_TOKEN = " repositories {";
    private static final String LOC_REPO_BEGIN = "maven { url new File('";
    private static final String LOC_REPO_END = "').toURI().toURL() }";

    /**
     * Shortcut for {@link #retrieveProjectInfo(File projectDir, String initScriptPath)}, where initScriptPath is null
     *
     * @param projectDir directory with Gradle project to analyse
     * @return project information in ProjectRoot format
     */
    public ProjectRoot retrieveProjectInfo(File projectDir) {
        return retrieveProjectInfo(projectDir, null);
    }

    /**
     * Retrieves ProjectRoot from Gradle project in the given directory.
     *
     * @param projectDir directory with Gradle project to analyse
     * @param localRepoPath (optional) location of additional maven repository to look for plugin implementation
     * @return project information in ProjectRoot format
     */
    public ProjectRoot retrieveProjectInfo(File projectDir, String localRepoPath) {

        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(projectDir);
        ProjectConnection connection = null;

        File initScript;
        try {
            initScript = prepareInitScript(localRepoPath);
        } catch (IOException e) {
            throw new GradleException("Failed to store init script to a tmp file", e);
        }

        try {
            connection = connector.connect();
            ModelBuilder<ProjectInfoModel> customModelBuilder = connection.model(ProjectInfoModel.class);
            customModelBuilder.withArguments("--init-script", initScript.getAbsolutePath());
            ProjectInfoModel model = customModelBuilder.get();
            if (model == null) {
                throw new GradleException("Failed to retrieve ProjectInfoModel from " + projectDir.getAbsolutePath());
            }

            String json = model.getRootProjectJson();
            initScript.delete();

            return ProjectJson.fromJson(json);

        } finally {
            if(connection != null) {
                connection.close();
            }
        }

    }

    private File prepareInitScript(String localRepoPath) throws IOException {

        String content = ResourceLoaderUtil.loadResourceContent(getClass(), "com/jvr/gradle/invoker/init.gradle");
        if (localRepoPath != null) {
            String extraRepo = String.format("%s %s%s%s", REPLACE_TOKEN, LOC_REPO_BEGIN, localRepoPath, LOC_REPO_END);
            content = content.replace(REPLACE_TOKEN, extraRepo);
        }

        File initFile = File.createTempFile("project-info-init", "gradle");
        ResourceLoaderUtil.stringToFile(initFile, content);

        return initFile;
    }

}