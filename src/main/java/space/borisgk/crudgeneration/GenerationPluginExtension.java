package space.borisgk.crudgeneration;

import java.util.List;

public class GenerationPluginExtension {
    protected String srcRoot;
    protected String generationRoot;
    protected String modelPackage;
    protected List<String> generationPackages;
    protected List<String> generationTemplates;

    public List<String> getGenerationPackages() {
        return generationPackages;
    }

    public void setGenerationPackages(List<String> generationPackages) {
        this.generationPackages = generationPackages;
    }

    public List<String> getGenerationTemplates() {
        return generationTemplates;
    }

    public void setGenerationTemplates(List<String> generationTemplates) {
        this.generationTemplates = generationTemplates;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getGenerationRoot() {
        return generationRoot;
    }

    public void setGenerationRoot(String generationRoot) {
        this.generationRoot = generationRoot;
    }

    public String getSrcRoot() {
        return srcRoot;
    }

    public void setSrcRoot(String srcRoot) {
        this.srcRoot = srcRoot;
    }

    public boolean checkSetUp() {
        return srcRoot != null &&
                generationRoot != null &&
                generationPackages != null &&
                generationTemplates != null &&
                generationPackages.size() != 0 &&
                generationTemplates.size() == generationPackages.size();
    }
}
