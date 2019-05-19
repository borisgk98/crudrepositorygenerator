package space.borisgk.gradletestplugin;

import org.gradle.api.GradleException;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;
import space.borisgk.gradletestplugin.models.JavaClass;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        GeneratorEnv generatorEnv = new GeneratorEnv();
        try {
            generatorEnv.setUp(new GenerationPluginExtension(){{
                srcRoot = "/home/boris/progs/work/autoshowroom/server/build/classes/java/main";
                srcPackage = "com.mera.borisgk98.autoshowroom.server.rest.api";
                generationRoot = "/mnt/D/my programms/gradle/crudrepositorygenerator/src/main/java";
                generationPackage = "test.generation";
            }});
        }
        catch (GenerationPluginException e) {
            throw new GradleException("Bad generation params", e);
        }
        Generator generator = new Generator(generatorEnv);
        try {
            generator.generate();
        }
        catch (GenerationPluginException e) {
            throw new GradleException("Error while generation classes", e);
        }
    }
}
