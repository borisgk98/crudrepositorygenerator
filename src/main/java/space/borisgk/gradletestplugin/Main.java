package space.borisgk.gradletestplugin;

import org.gradle.api.GradleException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import space.borisgk.gradletestplugin.classloader.ClassLoader;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Generator generator = new Generator();
        try {
            generator.setUp(new GenerationPluginExtension(){{
                srcRoot = "/mnt/D/my programms/gradle/crudrepositorygenerator/build/classes/java/main";
                srcPackage = "space.borisgk.gradletestplugin";
                generationRoot = "/mnt/D/my programms/gradle/crudrepositorygenerator/src/main/java";
                generationPackage = "test.generation";
            }});
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
