package space.borisgk.crudgeneration;

import space.borisgk.crudgeneration.exception.GenerationPluginException;
import space.borisgk.crudgeneration.util.PackageToPathConverter;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GeneratorEnv {
    private File srcPackageDir, generationPackageDir, srcDir, generationDir;
    private String generationPackage, srcPackage, servicesPackage;

    private PackageToPathConverter converter = new PackageToPathConverter();
    private final Logger logger = Context.logger;
    private List<String> apiClasses;
    private String imports;

    public String getImports() {
        return imports;
    }

    public String getServicesPackage() {
        return servicesPackage;
    }

    public void setUp(GenerationPluginExtension e) throws GenerationPluginException {
        generationPackage = e.getGenerationPackage();
        srcPackage = e.getSrcPackage();
        srcDir = Paths.get(e.getSrcRoot()).toFile();
        if (!srcDir.exists()) {
            throw new GenerationPluginException(String.format("srcRoot %s does not exist", e.getSrcRoot()));
        }
        if (srcDir.isFile()) {
            throw new GenerationPluginException(String.format("srcRoot %s should be dir", e.getSrcRoot()));
        }
        srcPackageDir = srcDir.toPath().resolve(converter.convert(e.getSrcPackage())).toFile();
        if (!srcPackageDir.exists() || srcPackageDir.isFile()) {
            throw new GenerationPluginException(String.format("srcPackage %s does not exist", e.getSrcPackage()));
        }
        generationDir = Paths.get(e.getGenerationRoot()).toFile();
        if (!generationDir.exists()) {
            generationDir.mkdirs();
        }
        generationPackageDir = generationDir.toPath().resolve(converter.convert(e.getGenerationPackage())).toFile();
        if (!generationPackageDir.exists()) {
            generationPackageDir.mkdirs();
        }
        apiClasses = getClassNamesFromPackage(srcPackageDir);
        if (apiClasses.isEmpty()) {
            throw new GenerationPluginException("Cannot find Api classes");
        }
        servicesPackage = e.getServicesPackage();
        imports = e.getImportsString();
    }


    public List<String> getApiClasses() {
        return apiClasses;
    }

    private List<String> getClassNamesFromPackage(File dir) {
        List<String> apiClasses = new ArrayList<>();
        for (File file : dir.listFiles()) {
            try {
                apiClasses.add(getClassName(file));
            }
            catch (IllegalArgumentException e) {
            }
        }
        return apiClasses;
    }

    private String getClassName(File file) {
        String fileName = file.getName();
        if (!fileName.substring(fileName.length() - 5).equals(".java")) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile("^.*Api.java$");
        if (!pattern.matcher(fileName).matches()) {
            throw new IllegalArgumentException();
        }
        return fileName.substring(0, fileName.length() - 5);
    }

    public File getSrcPackageDir() {
        return srcPackageDir;
    }

    public File getGenerationPackageDir() {
        return generationPackageDir;
    }

    public File getSrcDir() {
        return srcDir;
    }

    public File getGenerationDir() {
        return generationDir;
    }

    public String getGenerationPackage() {
        return generationPackage;
    }

    public String getSrcPackage() {
        return srcPackage;
    }
}
