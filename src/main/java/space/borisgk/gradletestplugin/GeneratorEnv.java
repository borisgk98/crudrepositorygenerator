package space.borisgk.gradletestplugin;

import space.borisgk.gradletestplugin.classloader.GenerationClassLoader;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;
import space.borisgk.gradletestplugin.util.PackageToPathConverter;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GeneratorEnv {
    private File srcPackageDir, generationPackageDir, srcDir, generationDir;
    private String generationPackage, srcPackage;

    private PackageToPathConverter converter = new PackageToPathConverter();
    private GenerationClassLoader generationClassLoader;
    private final Logger logger = Context.logger;
    private List<Class> apiClasses;
    private String imports;

    public String getImports() {
        return imports;
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
        generationPackageDir = srcDir.toPath().resolve(converter.convert(e.getGenerationPackage())).toFile();
        if (!generationPackageDir.exists()) {
            generationPackageDir.mkdirs();
        }
        generationClassLoader = new GenerationClassLoader(srcDir, srcPackage);
        apiClasses = getApiClassesPrivate();
        if (apiClasses.isEmpty()) {
            throw new GenerationPluginException("Cannot find Api classes");
        }
        imports = e.getImportsString();
    }

    public List<Class> getApiClasses() {
        return apiClasses;
    }

    private List<Class> getApiClassesPrivate() {
        List<Class> apiClasses = new ArrayList<>();
        for (File file : srcPackageDir.listFiles()) {
            try {
                Class c = generationClassLoader.loadClass(getClassName(file));
                if (c == null) {
                    continue;
                }
                apiClasses.add(c);
            }
            catch (Exception e) {
                logger.warning(String.format("Cannot get class from %s", file.getAbsolutePath()));
            }
        }
        return apiClasses;
    }

    private String getClassName(File file) {
        String fileName = file.getName();
        if (!fileName.substring(fileName.length() - 6).equals(".class")) {
            throw new IllegalArgumentException();
        }
        return srcPackage + "." + fileName.substring(0, fileName.length() - 6);
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
