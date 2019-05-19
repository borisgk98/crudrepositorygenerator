package space.borisgk.gradletestplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

public class GenerationTask extends DefaultTask {
    public GenerationTask() {
    }

    @TaskAction
    public void generate() {
        GenerationPluginExtension extension = (GenerationPluginExtension)this.getProject().getExtensions().findByType(GenerationPluginExtension.class);
        if (extension == null) {
            extension = new GenerationPluginExtension();
        }

        String root = extension.getSrcRoot();
        String pack = extension.getGenerationPackage();
        System.out.println(root);
        System.out.println(pack);
        String packDir = pack.replace('.', '/');
        System.out.println(packDir);
        File rootDir = Paths.get(root, packDir).toFile();
        System.out.println(rootDir);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        JavaClass javaClass = new JavaClass("HelloWord", pack);
        System.out.println(pack);
        System.out.println(javaClass);
        File helloWorldClass = rootDir.toPath().resolve(javaClass.name + ".java").toFile();
        if (!helloWorldClass.exists()) {
            try {
                helloWorldClass.createNewFile();
            } catch (IOException var11) {
                throw new GradleException("Cannot create " + helloWorldClass.getAbsolutePath(), var11);
            }
        }

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(helloWorldClass);
        } catch (FileNotFoundException var10) {
            throw new GradleException("File not found " + helloWorldClass.getAbsolutePath(), var10);
        }

        pw.println(javaClass.toString());
        pw.flush();
    }
}
