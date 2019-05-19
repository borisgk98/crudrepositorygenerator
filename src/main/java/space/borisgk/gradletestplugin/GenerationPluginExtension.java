package space.borisgk.gradletestplugin;

public class GenerationPluginExtension {
    private String srcRoot;
    private String generationPackage;

    public GenerationPluginExtension() {
    }

    public String getGenerationPackage() {
        return this.generationPackage;
    }

    public void setGenerationPackage(String generationPackage) {
        this.generationPackage = generationPackage;
    }

    public void setSrcRoot(String srcRoot) {
        this.srcRoot = srcRoot;
    }

    public String getSrcRoot() {
        return this.srcRoot;
    }
}
