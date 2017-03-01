package com.jvr.maven.plugins.info;

import com.jvr.build.info.api.Module;
import com.jvr.build.info.api.Project;
import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.BuildingDependencyNodeVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.jvr.build.info.api.Constants.TASK_NAME;

@Mojo(name = TASK_NAME, aggregator = true, requiresDependencyResolution = ResolutionScope.TEST, threadSafe = true)
public class InfoMojo extends AbstractMojo {
    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    /**
     * The dependency tree builder to use.
     */
    @Component(hint = "default")
    private DependencyGraphBuilder dependencyGraphBuilder;

    /**
     * If specified, this parameter will cause the dependency tree to be written to the path specified, instead of
     * writing to the console.
     */
    @Parameter(property = "outputFile")
    private File outputFile;

    /**
     * If specified, this parameter will cause the dependency tree to be written using the specified format. Currently
     * supported format is: <code>json</code>.
     */
    @Parameter(property = "outputType", defaultValue = "json")
    private String outputType;

    /**
     * Enables pretty printing of the output
     */
    @Parameter(property = "pretty", defaultValue = "false")
    private boolean pretty;

    /**
     * The scope to filter by when resolving the dependency tree, or <code>null</code> to include dependencies from
     * all scopes. Note that this feature does not currently work due to MSHARED-4
     *
     * @see <a href="https://issues.apache.org/jira/browse/MSHARED-4">MSHARED-4</a>
     */
    @Parameter(property = "scope")
    private String scope;

    /**
     * Skip plugin execution completely.
     */
    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    /*
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping plugin execution");
            return;
        }

        try {

            ArtifactFilter artifactFilter = (scope != null) ? new ScopeArtifactFilter(scope) : null;
            ProjectRoot dependencyTree = serializeDependencyTree(project, artifactFilter);

            String dependencyTreeString = "";
            if ("json".equals(outputType)) {
                dependencyTreeString = ProjectJson.toString(dependencyTree, pretty);
            }

            if (outputFile != null) {
                FileUtils.writeStringToFile(outputFile, dependencyTreeString, Charset.defaultCharset());
                getLog().info("Wrote dependency tree to: " + outputFile);
            } else {
                System.out.print(dependencyTreeString);
            }

        } catch (IOException exception) {
            throw new MojoExecutionException("Cannot serialise project dependency graph", exception);
        }
    }

    /**
     * Serializes the specified dependency tree to a string.
     * @return object with dependency tree
     */
    private ProjectRoot serializeDependencyTree(MavenProject project, ArtifactFilter artifactFilter) throws MojoExecutionException {

        ProjectBuildingRequest buildingRequest =
                new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());

        buildingRequest.setProject(project);

        // non-verbose mode use dependency graph component, which gives consistent results with Maven version running
        DependencyNode rootNode;
        try {
            rootNode = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, artifactFilter, session.getProjects());
        } catch (DependencyGraphBuilderException exception) {
            throw new MojoExecutionException("Cannot build project dependency graph", exception);
        }

        MavenNodeVisitor visitor = new MavenNodeVisitor();
        rootNode.accept(new BuildingDependencyNodeVisitor(visitor));
        ProjectRoot rootProject = visitor.getRoot();

        if (rootProject != null) {

            rootProject.setName(project.getName());
            rootProject.setDescription(project.getDescription());
            if (project.getParent() != null) {
                Project parent = MavenNodeVisitor.toProject(project.getParent().getArtifact());
                rootProject.setParent(parent);
            }

            for (String moduleFolder : project.getModules()) {
                MavenProject moduleProject;
                try {
                    moduleProject = findModuleProject(project, moduleFolder);
                } catch (IOException e) {
                    throw new MojoExecutionException(String.format("IO exception attempting to find %s module", moduleFolder), e);
                }
                ProjectRoot module = serializeDependencyTree(moduleProject, artifactFilter);
                rootProject.addModule(new Module(moduleFolder, module));
            }

        }

        return rootProject;

    }

    /**
     * Finds the module project among projects in the session
     *
     * @param project      to find module for
     * @param moduleFolder name of the module to find
     * @return Maven Project of the module
     */
    private MavenProject findModuleProject(MavenProject project, String moduleFolder) throws IOException, MojoExecutionException {

        File projectDir = project.getFile().getParentFile();
        String moduleDirName = new File(projectDir, moduleFolder).getCanonicalPath();

        List<MavenProject> projects = session.getProjects();
        MavenProject module = null;
        for (MavenProject candidate : projects) {
            if (candidate.getFile() != null) {
                String candidateDir = candidate.getFile().getParentFile().getCanonicalPath();
                if (candidateDir.equals(moduleDirName)) {
                    module = candidate;
                    break;
                }
            }
        }

        if (module == null) {
            throw new MojoExecutionException("No project found in " + moduleDirName);
        }

        return module;

    }

}
