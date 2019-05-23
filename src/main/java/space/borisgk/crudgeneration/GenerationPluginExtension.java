package space.borisgk.crudgeneration;

import java.util.List;

public class GenerationPluginExtension {
    protected String srcRoot;
    protected String generationRoot;
    protected String modelPackage;
    protected List<String> generationPackages;
    protected List<String> generationTemplates;
    protected List<String> generationConcatOuts;
    protected List<String> generationConcatTemplates;
    protected List<String> excludeModels;

    public GenerationPluginExtension() {
    }

    public List<String> getExcludeModels() {
        return this.excludeModels;
    }

    public void setExcludeModels(List<String> excludeModels) {
        this.excludeModels = excludeModels;
    }

    public List<String> getGenerationPackages() {
        return this.generationPackages;
    }

    public void setGenerationPackages(List<String> generationPackages) {
        this.generationPackages = generationPackages;
    }

    public List<String> getGenerationConcatOuts() {
        return this.generationConcatOuts;
    }

    public void setGenerationConcatOuts(List<String> generationConcatOuts) {
        this.generationConcatOuts = generationConcatOuts;
    }

    public List<String> getGenerationConcatTemplates() {
        return this.generationConcatTemplates;
    }

    public void setGenerationConcatTemplates(List<String> generationConcatTemplates) {
        this.generationConcatTemplates = generationConcatTemplates;
    }

    public List<String> getGenerationTemplates() {
        return this.generationTemplates;
    }

    public void setGenerationTemplates(List<String> generationTemplates) {
        this.generationTemplates = generationTemplates;
    }

    public String getModelPackage() {
        return this.modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getGenerationRoot() {
        return this.generationRoot;
    }

    public void setGenerationRoot(String generationRoot) {
        this.generationRoot = generationRoot;
    }

    public String getSrcRoot() {
        return this.srcRoot;
    }

    public void setSrcRoot(String srcRoot) {
        this.srcRoot = srcRoot;
    }

    public boolean checkSetUp() {
        return this.srcRoot != null && this.generationRoot != null && (this.generationPackages == null || this.generationTemplates == null || this.generationPackages.size() != 0 && this.generationTemplates.size() == this.generationPackages.size());
    }
}
