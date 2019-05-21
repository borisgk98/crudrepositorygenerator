package space.borisgk.crudgeneration;

import java.util.List;
import java.util.Map;

public class GenerationPluginExtension {
    protected String srcRoot;
    protected String generationRoot;
    protected String apiPackage;
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

    public String getApiPackage() {
        return apiPackage;
    }

    public void setApiPackage(String apiPackage) {
        this.apiPackage = apiPackage;
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
