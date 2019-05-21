package space.borisgk.crudgeneration;

import org.antlr.stringtemplate.StringTemplate;
import space.borisgk.crudgeneration.exception.GenerationPluginException;
import space.borisgk.crudgeneration.models.GenerationItem;

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
        for (GenerationItem item : generatorEnv.getGenerationItems()) {
            try {
//                String model = getModelNameFromClassName(apiClassName);
//                template.setAttribute("model", model);

                String newClassName = "";
                String res = template.toString();
                try {
                    File file = item.getGenerationFileOut();
                    logger.info(String.format("Write new class to %s", file.toPath().toString()));
                    Files.write(file.toPath(), res.getBytes());
                }
                catch (IOException e) {
                    logger.warning("Cannot write file " + "");
                }
            }
            finally {
                template.reset();
            }
        }
    }
}
