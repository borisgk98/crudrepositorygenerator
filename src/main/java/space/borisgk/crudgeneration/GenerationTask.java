package space.borisgk.crudgeneration;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import space.borisgk.crudgeneration.exception.GenerationPluginException;

public class GenerationTask extends DefaultTask {

    public GenerationTask() {
    }

    @TaskAction
    public void generate() {
        GenerationPluginExtension extension = this.getProject().getExtensions().findByType(GenerationPluginExtension.class);
        if (extension == null || !extension.checkSetUp()) {
            throw new GradleException("Please set up crudGenerationSetting");
//            extension = new GenerationPluginExtension();
        }

        GeneratorEnv generatorEnv = new GeneratorEnv();
        try {
            generatorEnv.setUp(extension);
        }
        catch (GenerationPluginException e) {
            throw new GradleException("Bad generation params", e);
        }
        Generator generator = new Generator(generatorEnv);
    }
}
