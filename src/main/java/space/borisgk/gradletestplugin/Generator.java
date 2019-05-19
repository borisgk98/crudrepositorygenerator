package space.borisgk.gradletestplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.borisgk.gradletestplugin.exception.GenerationPluginException;
import space.borisgk.gradletestplugin.util.Converter;
import space.borisgk.gradletestplugin.util.PackageToPathConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class Generator {
    private File srcPackageDir, generationPackageDir, srcDir, generationDir;
    private String generationPackage, srcPackage;

    @Autowired
    private PackageToPathConverter converter;
    @Autowired
    private ClassLoader classLoader;
    @Autowired
    private Logger logger = LoggerFactory.getLogger(Generator.class);

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
        if (!srcPackageDir.exists() || !srcPackageDir.isFile()) {
            throw new GenerationPluginException(String.format("srcPackage %s does not exist", e.getSrcPackage()));
        }
        generationDir = Paths.get(e.getGenerationRoot()).toFile();
        if (!generationDir.exists()) {
            generationDir.mkdirs();
        }
        srcPackageDir = srcDir.toPath().resolve(converter.convert(e.getGenerationPackage())).toFile();
        if (!srcPackageDir.exists()) {
            srcPackageDir.mkdirs();
        }
    }

    public List<Class> getApiClasses() {
        List<Class> apiClasses = new ArrayList<>();
        for (File file : srcPackageDir.listFiles()) {
            try {
                apiClasses.add(classLoader.loadClass(file.getName()));
            }
            catch (Exception e) {
                logger.warn(String.format("Cannot get class from %s", file.getAbsolutePath()));
            }
        }
        return apiClasses;
    }

    private String getClassName(File file) {
        String fileName = file.getName();
        if (!fileName.substring(fileName.length() - 6).equals(".class")) {
            throw new IllegalArgumentException();
        }
        return srcPackage + "." + fileName;
    }
}
