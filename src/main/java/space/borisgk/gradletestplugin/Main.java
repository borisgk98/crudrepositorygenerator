package space.borisgk.gradletestplugin;

import org.gradle.api.GradleException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import space.borisgk.gradletestplugin.classloader.ClassLoader;
import space.borisgk.gradletestplugin.config.Config;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext cxt = new AnnotationConfigApplicationContext(Config.class);
        Generator env = cxt.getBean(Generator.class);
        try {
            env.setUp(GenerationPluginExtension.builder()
                    .srcRoot("/mnt/D/my programms/gradle/testplugin/build/classes/java/main")
                    .srcPackage("space.borisgk.gradletestplugin")
                    .generationPackage("test.test")
                    .generationRoot("/mnt/D/my programms/gradle/testplugin/src/main/java")
                    .build());
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
