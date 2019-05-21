package space.borisgk.crudgeneration;

import org.antlr.stringtemplate.StringTemplate;
import space.borisgk.crudgeneration.exception.GenerationPluginException;
import space.borisgk.crudgeneration.models.GenerationItem;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.logging.Logger;

public class Generator {
    protected GeneratorEnv env;
    protected final Logger logger = Context.logger;

    public Generator(GeneratorEnv generatorEnv) {
        this.env = generatorEnv;
    }

    public void generate() throws GenerationPluginException {
        for (GenerationItem item : env.getGenerationItems()) {
            logger.info(String.format("Generate java file %s from template %s and model %s",
                    item.getGenerationFileOut(),
                    item.getGenerationTemplateSrc(),
                    item.getModel()));
            try {
                String templateString = getFileData(item.getGenerationTemplateSrc());
                StringTemplate template = new StringTemplate(templateString);
                setUpTemplate(template, item.getModel());
                String res = template.toString();
                File file = item.getGenerationFileOut();
                writeStringDateToFile(file, res);
            }
            catch (Exception e) {
                logger.warning(String.format("Cannot generate new java file for" +
                        " model %s and for template %s", item.getModel(), item.getGenerationTemplateSrc().toPath()));
            }
        }

        if (env.getYamlTemplateSrc() != null) {
            try {
                    StringBuilder builder = new StringBuilder();
                    StringTemplate template = new StringTemplate(getFileData(env.getYamlTemplateSrc()));
                    for (String m : env.getModels()) {
                        setUpTemplate(template, m);
                        builder.append(template.toString());
                        template.reset();
                    }
                    writeStringDateToFile(env.getYamlOut(), builder.toString());
            }
            catch (Exception e) {
                logger.warning(String.format("Cannot generate new yaml file for" +
                        " yaml template %s and for yaml out %s", env.getYamlTemplateSrc(), env.getYamlOut()));
            }
        }
    }

    protected String getFileData(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    protected void writeStringDateToFile(File file, String s) {
        try {
            logger.info(String.format("Write new class to %s", file.toPath().toString()));
            Files.write(file.toPath(), s.getBytes());
        }
        catch (IOException e) {
            logger.warning("Cannot write file " + file);
        }
    }

    protected void setUpTemplate(StringTemplate template, String model) {
        template.setAttribute("model", model.toLowerCase());
        template.setAttribute("Model", model);
    }
}
