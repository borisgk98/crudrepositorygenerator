package space.borisgk.gradletestplugin;

public class GenerationPluginExtension {
    private String srcRoot;
    private String generationPackage;
    private String srcPackage;
    private String generationRoot;

    public String getSrcPackage() {
        return srcPackage;
    }

    public void setSrcPackage(String srcPackage) {
        this.srcPackage = srcPackage;
    }

    public String getGenerationRoot() {
        return generationRoot;
    }

    public void setGenerationRoot(String generationRoot) {
        this.generationRoot = generationRoot;
    }

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
