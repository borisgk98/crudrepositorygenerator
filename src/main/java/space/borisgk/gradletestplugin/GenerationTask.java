package space.borisgk.gradletestplugin;

import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;

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

        Generator generator = new Generator();
        try {
            generator.setUp(extension);
        }
        catch (GenerationPluginException e) {
            throw new GradleException("Bad generation params", e);
        }
        List<Class> apiClasses = generator.getApiClasses();
        for (Class c : apiClasses) {
            System.out.println(c.getName());
        }
    }
}
