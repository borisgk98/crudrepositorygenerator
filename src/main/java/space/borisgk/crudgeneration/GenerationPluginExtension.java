package space.borisgk.crudgeneration;

import java.util.List;

public class GenerationPluginExtension {
    protected String srcRoot;
    protected String generationRoot;
    protected String modelPackage;
    protected String yamlPathTemplateSrc;
    protected String yamlGenerationOut;
    protected List<String> generationPackages;
    protected List<String> generationTemplates;
    protected List<String> excludeModels;

    public List<String> getExcludeModels() {
        return excludeModels;
    }

    public void setExcludeModels(List<String> excludeModels) {
        this.excludeModels = excludeModels;
    }

    public List<String> getGenerationPackages() {
        return generationPackages;
    }

    public void setGenerationPackages(List<String> generationPackages) {
        this.generationPackages = generationPackages;
    }

    public String getYamlPathTemplateSrc() {
        return yamlPathTemplateSrc;
    }

    public void setYamlPathTemplateSrc(String yamlPathTemplateSrc) {
        this.yamlPathTemplateSrc = yamlPathTemplateSrc;
    }

    public String getYamlGenerationOut() {
        return yamlGenerationOut;
    }

    public void setYamlGenerationOut(String yamlGenerationOut) {
        this.yamlGenerationOut = yamlGenerationOut;
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
