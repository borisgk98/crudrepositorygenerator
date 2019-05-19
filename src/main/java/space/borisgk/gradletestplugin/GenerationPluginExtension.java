package space.borisgk.gradletestplugin;

import java.util.List;

public class GenerationPluginExtension {
    protected String srcRoot;
    protected String generationPackage;
    protected String srcPackage;
    protected String generationRoot;
    protected String servicesPackage;
    protected String importsString;

    public String getImportsString() {
        return importsString;
    }

    public void setImportsString(String importsString) {
        this.importsString = importsString;
    }

    public String getServicesPackage() {
        return servicesPackage;
    }

    public void setServicesPackage(String servicesPackage) {
        this.servicesPackage = servicesPackage;
    }

    public String getGenerationPackage() {
        return generationPackage;
    }

    public void setGenerationPackage(String generationPackage) {
        this.generationPackage = generationPackage;
    }

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

    public String getSrcRoot() {
        return srcRoot;
    }

    public void setSrcRoot(String srcRoot) {
        this.srcRoot = srcRoot;
    }

    public boolean checkSetUp() {
        return srcRoot != null && srcPackage != null && generationPackage != null && generationRoot != null;
    }
}
