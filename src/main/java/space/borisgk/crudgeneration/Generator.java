package space.borisgk.crudgeneration;

import org.antlr.stringtemplate.StringTemplate;
import space.borisgk.crudgeneration.exception.GenerationPluginException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Generator {
    protected GeneratorEnv generatorEnv;
    protected final Logger logger = Context.logger;

    public Generator(GeneratorEnv generatorEnv) {
        this.generatorEnv = generatorEnv;
    }

    public void generate() throws GenerationPluginException {
        String resName = "ControllerTemplate.java.example";
        URL url = getClass()
                .getClassLoader()
                .getResource(resName);
        InputStream input = null;
        try {
            URLConnection connection = url.openConnection();
            input = connection.getInputStream();
        }
        catch (IOException e) {
            throw new GenerationPluginException("Cannot find " + resName);
        }
        String templateString = (new BufferedReader(new InputStreamReader(input))).lines().collect(Collectors.joining(System.lineSeparator()));
        StringTemplate template = new StringTemplate(templateString);
        for (String apiClass : generatorEnv.getApiClasses()) {
            if (apiClass == null) {
                continue;
            }
            try {
                String apiClassName = apiClass;
                String model = getModelNameFromClassName(apiClassName);
                template.setAttribute("srcPackage", generatorEnv.getSrcPackage());
                template.setAttribute("model", model);
                template.setAttribute("package", generatorEnv.getGenerationPackage());
                template.setAttribute("srcClassName", apiClassName);
                template.setAttribute("imports", generatorEnv.getImports());
                template.setAttribute("generatorName", Generator.class.getName());
                template.setAttribute("date", new Date());
                String newClassName = model + "ApiController";
                template.setAttribute("className", newClassName);
                template.setAttribute("servicesPackage", generatorEnv.getServicesPackage());

                String res = template.toString();
                Path path = generatorEnv.getGenerationPackageDir().toPath().resolve(newClassName + ".java");
                try {
                    File file = path.toFile();
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    logger.info(String.format("Write new class to %s", path.toString()));
                    Files.write(path, res.getBytes());
                }
                catch (IOException e) {
                    logger.warning("Cannot write file " + path);
                }
            }
            finally {
                template.reset();
            }
        }
    }

    protected String getModelNameFromClassName(String className) {
        return className.substring(0, className.length() - 3);
    }
}
