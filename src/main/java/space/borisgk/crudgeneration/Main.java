package space.borisgk.crudgeneration;

import java.util.ArrayList;
import org.gradle.api.GradleException;
import space.borisgk.crudgeneration.exception.GenerationPluginException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        GeneratorEnv generatorEnv = new GeneratorEnv();

        try {
            generatorEnv.setUp(new GenerationPluginExtension() {
                {
                    this.srcRoot = "/home/boris/progs/autoshowroom/server/src/main/java";
                    this.modelPackage = "/home/boris/progs/autoshowroom/server/src/main/java/com/mera/borisgk98/autoshowroom/server/models";
                    this.generationRoot = "/home/boris/progs/crudrepositorygenerator/src/main/java";
                    this.generationConcatOuts = new ArrayList<String>() {
                        {
                            this.add("/home/boris/progs/crudrepositorygenerator/src/main/resources/out");
                        }
                    };
                    this.generationConcatTemplates = new ArrayList<String>() {
                        {
                            this.add("/home/boris/progs/crudrepositorygenerator/src/main/resources/Exapmle");
                        }
                    };
                }
            });
        } catch (GenerationPluginException var5) {
            throw new GradleException("Bad generation params", var5);
        }

        Generator generator = new Generator(generatorEnv);

        try {
            generator.generate();
        } catch (GenerationPluginException var4) {
            throw new GradleException("Error while generation classes", var4);
        }
    }
}
