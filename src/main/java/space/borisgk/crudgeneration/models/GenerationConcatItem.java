package space.borisgk.crudgeneration.models;

import java.io.File;
import java.util.List;

public class GenerationConcatItem {
    private List<String> models;
    private File generationFileOut;
    private File generationTemplateSrc;

    public GenerationConcatItem(List<String> models, File generationFileOut, File generationTemplateSrc) {
        this.models = models;
        this.generationFileOut = generationFileOut;
        this.generationTemplateSrc = generationTemplateSrc;
    }

    public File getGenerationFileOut() {
        return generationFileOut;
    }

    public File getGenerationTemplateSrc() {
        return generationTemplateSrc;
    }

    public List<String> getModels() {
        return models;
    }
}
