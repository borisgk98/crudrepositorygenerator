package space.borisgk.crudgeneration.models;

import java.io.File;

public class GenerationItem {
    private String model;
    private File generationFileOut;
    private File generationTemplateSrc;

    public GenerationItem(String model, File generationFileOut, File generationTemplateSrc) {
        this.model = model;
        this.generationFileOut = generationFileOut;
        this.generationTemplateSrc = generationTemplateSrc;
    }

    public File getGenerationFileOut() {
        return generationFileOut;
    }

    public String getModel() {
        return model;
    }

    public File getGenerationTemplateSrc() {
        return generationTemplateSrc;
    }
}
