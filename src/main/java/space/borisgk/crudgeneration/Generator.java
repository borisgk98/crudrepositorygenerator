package space.borisgk.crudgeneration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Iterator;
import java.util.logging.Logger;
import org.antlr.stringtemplate.StringTemplate;
import space.borisgk.crudgeneration.exception.GenerationPluginException;
import space.borisgk.crudgeneration.models.GenerationConcatItem;
import space.borisgk.crudgeneration.models.GenerationItem;

public class Generator {
    protected GeneratorEnv env;
    protected final Logger logger;

    public Generator(GeneratorEnv generatorEnv) {
        this.logger = Context.logger;
        this.env = generatorEnv;
    }

    public void generate() throws GenerationPluginException {
        Iterator var1 = this.env.getGenerationItems().iterator();

        StringTemplate template;
        while(var1.hasNext()) {
            GenerationItem item = (GenerationItem)var1.next();
            this.logger.info(String.format("Generate java file %s from template %s and model %s", item.getGenerationFileOut(), item.getGenerationTemplateSrc(), item.getModel()));

            try {
                String templateString = this.getFileData(item.getGenerationTemplateSrc());
                template = new StringTemplate(templateString);
                this.setUpTemplate(template, item.getModel());
                String res = template.toString();
                File file = item.getGenerationFileOut();
                this.writeStringDateToFile(file, res);
            } catch (Exception var7) {
                this.logger.warning(String.format("Cannot generate new java file for model %s and for template %s", item.getModel(), item.getGenerationTemplateSrc().toPath()));
            }
        }

        var1 = this.env.getGenerationConcatItems().iterator();

        while(var1.hasNext()) {
            GenerationConcatItem item = (GenerationConcatItem)var1.next();

            try {
                StringBuilder builder = new StringBuilder();
                template = new StringTemplate(this.getFileData(item.getGenerationTemplateSrc()));
                Iterator var11 = item.getModels().iterator();

                while(var11.hasNext()) {
                    String m = (String)var11.next();
                    this.setUpTemplate(template, m);
                    builder.append(template.toString());
                    template.reset();
                }

                this.writeStringDateToFile(item.getGenerationFileOut(), builder.toString());
            } catch (Exception var8) {
                this.logger.warning(String.format("Cannot generate new file for template %s and for out %s", item.getGenerationTemplateSrc(), item.getGenerationFileOut()));
            }
        }

    }

    protected String getFileData(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    protected void writeStringDateToFile(File file, String s) {
        try {
            this.logger.info(String.format("Write new class to %s", file.toPath().toString()));
            Files.write(file.toPath(), s.getBytes(), new OpenOption[0]);
        } catch (IOException var4) {
            this.logger.warning("Cannot write file " + file);
        }

    }

    protected void setUpTemplate(StringTemplate template, String model) {
        template.setAttribute("model", model.toLowerCase());
        template.setAttribute("Model", model);
        String modelUpperFirstLater = model.toLowerCase();
        modelUpperFirstLater = modelUpperFirstLater.substring(1);
        modelUpperFirstLater = model.charAt(0) + modelUpperFirstLater;
        template.setAttribute("Modelufl", modelUpperFirstLater);
    }
}
