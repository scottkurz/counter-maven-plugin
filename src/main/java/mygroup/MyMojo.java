package mygroup;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "countTargets", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresDependencyResolution = ResolutionScope.TEST )
// without this, even fixing the ReactorReader doesn't make a difference, because it checks that there's a compile phase in the build before allowing this workaround
@Execute(phase = LifecyclePhase.PROCESS_TEST_CLASSES)
public class MyMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;

    public void execute()
        throws MojoExecutionException
    {
        if (outputDirectory != null) {
            System.out.println("For outputDir = " + outputDirectory + ", output count = " + countFiles(outputDirectory));
        } else {
            System.out.println("skipping null outputDir");
        }

    }

    private static int countFiles(File dir) {
        int count = 0;
        if (dir.listFiles() != null) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                count += countFiles(file);
            } else {
                count++;
            }
        }
        }
        return count;
    }
    
}
