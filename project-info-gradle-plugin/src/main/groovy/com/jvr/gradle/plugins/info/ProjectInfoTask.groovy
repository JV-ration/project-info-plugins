package com.jvr.gradle.plugins.info

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.result.ResolutionResult
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.diagnostics.internal.graph.DependencyGraphRenderer
import org.gradle.api.tasks.diagnostics.internal.graph.NodeRenderer
import org.gradle.api.tasks.diagnostics.internal.graph.SimpleNodeRenderer
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableDependency
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableModuleResult
import org.gradle.internal.graph.GraphRenderer
import org.gradle.logging.StyledTextOutput
import org.gradle.logging.internal.StreamingStyledTextOutput
import org.gradle.util.GUtil

class ProjectInfoTask extends DefaultTask {

    StyledTextOutput textOutput

    ProjectInfoTask() {
        description = 'Generate unified information about the projects. See https://github.com/JV-ration/project-info-plugins'
        textOutput = new StreamingStyledTextOutput(new BufferedWriter(new PrintWriter(System.out)))
    }

    @TaskAction
    def infoTask() {
        Project project = getProject()
        for( Project aProject : project.allprojects) {
            projectInfo( aProject )
        }
    }

    def projectInfo(Project project) {

        Set<Configuration> projectConfigurations = project.getConfigurations()

        if( projectConfigurations.size() == 0) {
            println "Project ${project.name} does not have configurations"
        }

        for( Configuration configuration : projectConfigurations) {

            println "Dependencies of ${project.name} (${configuration.name})"

            DependencyGraphRenderer dependencyGraphRenderer = startConfiguration(configuration)

            ResolutionResult result = configuration.getIncoming().getResolutionResult()
            RenderableDependency root = new RenderableModuleResult(result.getRoot())
            renderNow(root, dependencyGraphRenderer)

            println()
        }

    }

    private static void renderNow(RenderableDependency root, DependencyGraphRenderer dependencyGraphRenderer) {
        if (root.getChildren().isEmpty()) {
            return;
        }
        dependencyGraphRenderer.render(root)
    }

    public DependencyGraphRenderer startConfiguration(final Configuration configuration) {

        GraphRenderer renderer = new GraphRenderer(getTextOutput())
        renderer.visit(new Action<StyledTextOutput>() {
            public void execute(StyledTextOutput styledTextOutput) {
                getTextOutput().text(configuration.getName())
                getTextOutput().text(getDescription(configuration))
            }
        }, true)

        NodeRenderer nodeRenderer = new SimpleNodeRenderer()
        new DependencyGraphRenderer(renderer, nodeRenderer)
    }

    private static String getDescription(Configuration configuration) {
        GUtil.isTrue(configuration.getDescription()) ? " - " + configuration.getDescription() : ""
    }

    public StyledTextOutput getTextOutput() {
        return textOutput
    }

}
