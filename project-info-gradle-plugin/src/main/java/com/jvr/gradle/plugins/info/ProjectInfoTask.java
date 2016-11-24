package com.jvr.gradle.plugins.info;

import com.jvr.build.info.api.Module;
import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;
import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.GradleScriptException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.result.ResolutionResult;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableDependency;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableModuleResult;
import org.gradle.logging.StyledTextOutput;
import org.gradle.logging.internal.StreamingStyledTextOutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

class ProjectInfoTask extends DefaultTask {

    private StyledTextOutput textOutput;

    private String outputType = "json";
    private boolean pretty = true;
    private File outputFile = null;

    public ProjectInfoTask() {
        setDescription("Generate unified information about the projects. See https://github.com/JV-ration/project-info-plugins");
        textOutput = new StreamingStyledTextOutput(new BufferedWriter(new PrintWriter(System.out)));
    }

    @TaskAction
    public void infoTask() {

        Project project = getProject();

        try {
            ProjectInfoModelBuilder builder = new ProjectInfoModelBuilder();
            ProjectRoot dependencyTree = builder.getProjectRoot(project);

            String dependencyTreeString = "";
            if ("json".equals(outputType)) {
                dependencyTreeString = ProjectJson.toString(dependencyTree, pretty);
            }

            if (outputFile != null) {
                FileUtils.writeStringToFile(outputFile, dependencyTreeString, "UTF-8");
                getTextOutput().text("Wrote dependency tree to: " + outputFile);
            } else {
                System.out.print(dependencyTreeString);
            }

        } catch (IOException exception) {
            throw new GradleScriptException("Cannot serialise project dependency graph", exception);
        }

    }

    private StyledTextOutput getTextOutput() {
        return textOutput;
    }

}
