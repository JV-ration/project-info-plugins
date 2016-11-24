package com.jvr.gradle.invoker;

import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;
import com.jvr.gradle.model.ProjectInfoModel;
import org.gradle.api.GradleException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.ModelBuilder;

import java.io.File;

public class GradleProjectInfoRetriever {

    public ProjectRoot retrieveProjectInfo(File projectDir, String initScriptPath) {

        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(projectDir);
        ProjectConnection connection = null;

        try {
            connection = connector.connect();
            ModelBuilder<ProjectInfoModel> customModelBuilder = connection.model(ProjectInfoModel.class);
            customModelBuilder.withArguments("--init-script", initScriptPath);
            ProjectInfoModel model = customModelBuilder.get();
            if (model == null) {
                throw new GradleException("Failed to retrieve ProjectInfoModel from " + projectDir.getAbsolutePath());
            }

            String json = model.getRootProjectJson();
            return ProjectJson.fromJson(json);

        } finally {
            if(connection != null) {
                connection.close();
            }
        }

    }

}