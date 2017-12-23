package com.jvr.maven.plugins.info;

import com.jvr.build.info.api.Constants;
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static com.jvr.TestConstants.LOCAL_REPO_PATH;

/**
 * Test of Maven plugin implementation
 */
public class MavenProjectInfoTest {

    @Test
    public void testEmptyMavenProject() throws VerificationException, IOException {

        File webappHome = ResourceExtractor.simpleExtractResources( getClass(), "/maven/empty" );
        Verifier verifier = new Verifier( webappHome.getAbsolutePath() );

//        File localRepo = new File(LOCAL_REPO_PATH);
//        String localRepoPath = localRepo.getCanonicalPath();
//        verifier.setLocalRepo(localRepoPath);
//        System.out.println("Local repo path is set to " + localRepoPath);

        boolean debugVerifier = Boolean.getBoolean( "verifier.maven.debug" );
        verifier.setMavenDebug( debugVerifier );
        verifier.setDebugJvm( Boolean.getBoolean( "verifier.debugJvm" ) );
        verifier.displayStreamBuffers();

        // execution
        verifier.setCliOptions(Collections.<String>emptyList());
        verifier.executeGoal("com.jv-ration.maven.plugins:project-info-maven-plugin:" + Constants.TASK_NAME);
        verifier.displayStreamBuffers();

        // tearing down
        verifier.resetStreams();

    }

}
