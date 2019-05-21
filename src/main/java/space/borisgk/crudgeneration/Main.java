package space.borisgk.crudgeneration;

import org.gradle.api.GradleException;
import space.borisgk.crudgeneration.exception.GenerationPluginException;

public class Main {
    public static void main(String[] args) {
        GeneratorEnv generatorEnv = new GeneratorEnv();
        try {
            generatorEnv.setUp(new GenerationPluginExtension(){{
                srcRoot = "/home/boris/progs/work/autoshowroom/server/src/main/java";
                generationRoot = "/home/boris/progs/work/autoshowroom/server/src/main/java";
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
