package com.jvr.gradle.plugins.info;

import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleScriptException;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

class ProjectInfoTask extends DefaultTask {

    private String outputType = "json";
    private boolean pretty = true;
    private File outputFile = null;

    public ProjectInfoTask() {
        setDescription("Generate unified information about the projects. See https://github.com/JV-ration/project-info-plugins");
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
                try (FileOutputStream out = openOutputStream(outputFile)) {
                    byte[] bytes = dependencyTreeString.getBytes(Charset.forName("UTF-8"));
                    out.write(bytes);
                }
            } else {
                System.out.print(dependencyTreeString);
            }

        } catch (IOException exception) {
            throw new GradleScriptException("Cannot serialise project dependency graph", exception);
        }

    }

    private static FileOutputStream openOutputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, false);
    }

}