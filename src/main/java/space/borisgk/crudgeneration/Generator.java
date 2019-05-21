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
        for (GenerationItem item : generatorEnv.getGenerationItems()) {
            logger.info(String.format("Generate java file %s from template %s and model %s",
                    item.getGenerationFileOut(),
                    item.getGenerationTemplateSrc(),
                    item.getModel()));
            try {
                String templateString = new String(Files.readAllBytes(item.getGenerationTemplateSrc().toPath()));
                StringTemplate template = new StringTemplate(templateString);
                template.setAttribute("model", item.getModel().toLowerCase());
                template.setAttribute("Model", item.getModel());
                String res = template.toString();
                File file = item.getGenerationFileOut();
                try {
                    logger.info(String.format("Write new class to %s", file.toPath().toString()));
                    Files.write(file.toPath(), res.getBytes());
                }
                catch (IOException e) {
                    logger.warning("Cannot write file " + file);
                }
            }
            catch (Exception e) {
                logger.warning(String.format("Cannot generate new java file for" +
                        " model %s and for template %s", item.getModel(), item.getGenerationTemplateSrc().toPath()));
            }
        }
    }
}
