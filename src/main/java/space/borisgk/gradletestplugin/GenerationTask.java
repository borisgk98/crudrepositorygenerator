package space.borisgk.gradletestplugin;

import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import space.borisgk.gradletestplugin.config.Config;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;

@Component
public class GenerationTask extends DefaultTask {

    public GenerationTask() {
    }

    @TaskAction
    public void generate() {
        GenerationPluginExtension extension = (GenerationPluginExtension)this.getProject().getExtensions().findByType(GenerationPluginExtension.class);
        if (extension == null) {
            throw new GradleException("Please set up crudGenerationSetting");
//            extension = new GenerationPluginExtension();
        }

        ApplicationContext cxt = new AnnotationConfigApplicationContext(Config.class);
        Generator env = cxt.getBean(Generator.class);
        try {
            env.setUp(extension);
        }
        catch (GenerationPluginException e) {
            throw new GradleException("Bad generation params", e);
        }
        List<Class> apiClasses = env.getApiClasses();
        for (Class c : apiClasses) {
            System.out.println(c.getName());
        }
    }
}
