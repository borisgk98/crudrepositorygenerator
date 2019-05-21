package space.borisgk.crudgeneration;

import space.borisgk.crudgeneration.exception.GenerationPluginException;
import space.borisgk.crudgeneration.models.GenerationItem;
import space.borisgk.crudgeneration.util.PackageToPathConverter;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorEnv {

    private PackageToPathConverter converter = new PackageToPathConverter();
    private final Logger logger = Context.logger;
    private List<GenerationItem> generationItems;

    public void setUp(GenerationPluginExtension e) throws GenerationPluginException {

//        generationPackage = e.getGenerationPackage();
        File srcDir = Paths.get(e.getSrcRoot()).toFile();
        if (!srcDir.exists()) {
            throw new GenerationPluginException(String.format("srcRoot %s does not exist", e.getSrcRoot()));
        }
        if (srcDir.isFile()) {
            throw new GenerationPluginException(String.format("srcRoot %s should be dir", e.getSrcRoot()));
        }
        File apiPackageDir = srcDir.toPath().resolve(converter.convert(e.getApiPackage())).toFile();
        if (!apiPackageDir.exists() || apiPackageDir.isFile()) {
            throw new GenerationPluginException(String.format("apiPackage %s does not exist", e.getApiPackage()));
        }
        List<String> models = getModelsFromApiPackage(apiPackageDir);
        for (String m : models) {
            System.out.println(m);
        }
    }

    private List<String> getModelsFromApiPackage(File dir) {
        List<String> apiClasses = new ArrayList<>();
        for (File file : dir.listFiles()) {
            try {
                apiClasses.add(getModelName(file));
            }
            catch (IllegalArgumentException e) {
            }
        }
        return apiClasses;
    }

    private String getModelName(File file) {
        String fileName = file.getName();
        if (!fileName.substring(fileName.length() - 5).equals(".java")) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile("^(.*)Api.java$");
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        return matcher.group(1);
    }

    public List<GenerationItem> getGenerationItems() {
        return generationItems;
    }
}
