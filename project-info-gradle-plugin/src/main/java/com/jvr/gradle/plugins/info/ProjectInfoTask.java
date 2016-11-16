package com.jvr.gradle.plugins.info;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.result.ResolutionResult;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.diagnostics.internal.graph.DependencyGraphRenderer;
import org.gradle.api.tasks.diagnostics.internal.graph.NodeRenderer;
import org.gradle.api.tasks.diagnostics.internal.graph.SimpleNodeRenderer;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableDependency;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableModuleResult;
import org.gradle.internal.graph.GraphRenderer;
import org.gradle.logging.StyledTextOutput;
import org.gradle.logging.internal.StreamingStyledTextOutput;
import org.gradle.util.GUtil;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Set;

class ProjectInfoTask extends DefaultTask {

    private StyledTextOutput textOutput;

    public ProjectInfoTask() {
        setDescription("Generate unified information about the projects. See https://github.com/JV-ration/project-info-plugins");
        textOutput = new StreamingStyledTextOutput(new BufferedWriter(new PrintWriter(System.out)));
    }

    @TaskAction
    public void  infoTask() {
        Project project = getProject();
        for( Project aProject : project.getAllprojects()) {
            projectInfo( aProject );
        }
    }

    private void projectInfo(Project project) {

        Set<Configuration> projectConfigurations = project.getConfigurations();

        if( projectConfigurations.size() == 0) {
            System.out.println(String.format("Project %s does not have configurations", project.getName()));
        }

        for( Configuration configuration : projectConfigurations) {

            System.out.println(String.format("Dependencies of %s (%s)", project.getName(), configuration.getName()));

            DependencyGraphRenderer dependencyGraphRenderer = startConfiguration(configuration);

            ResolutionResult result = configuration.getIncoming().getResolutionResult();
            RenderableDependency root = new RenderableModuleResult(result.getRoot());
            renderNow(root, dependencyGraphRenderer);

            System.out.println();
        }

    }

    private static void renderNow(RenderableDependency root, DependencyGraphRenderer dependencyGraphRenderer) {
        if (root.getChildren().isEmpty()) {
            return;
        }
        dependencyGraphRenderer.render(root);
    }

    private DependencyGraphRenderer startConfiguration(final Configuration configuration) {

        GraphRenderer renderer = new GraphRenderer(getTextOutput());
        renderer.visit(new Action<StyledTextOutput>() {
            public void execute(StyledTextOutput styledTextOutput) {
                getTextOutput().text(configuration.getName());
                getTextOutput().text(getDescription(configuration));
            }
        }, true);

        NodeRenderer nodeRenderer = new SimpleNodeRenderer();
        return new DependencyGraphRenderer(renderer, nodeRenderer);
    }

    private static String getDescription(Configuration configuration) {
        return GUtil.isTrue(configuration.getDescription()) ? " - " + configuration.getDescription() : "";
    }

    private StyledTextOutput getTextOutput() {
        return textOutput;
    }

}
