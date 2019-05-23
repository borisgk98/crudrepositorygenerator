package space.borisgk.crudgeneration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import space.borisgk.crudgeneration.exception.GenerationPluginException;
import space.borisgk.crudgeneration.models.GenerationConcatItem;
import space.borisgk.crudgeneration.models.GenerationItem;
import space.borisgk.crudgeneration.util.PackageToPathConverter;

public class GeneratorEnv {
    private PackageToPathConverter converter = new PackageToPathConverter();
    private final Logger logger;
    private List<GenerationItem> generationItems;
    private List<GenerationConcatItem> generationConcatItems;
    private List<String> models;
    protected List<String> excludeModels;

    public GeneratorEnv() {
        this.logger = Context.logger;
        this.generationItems = new ArrayList();
        this.generationConcatItems = new ArrayList();
    }

    public void setUp(GenerationPluginExtension e) throws GenerationPluginException {
        if (e.getExcludeModels() == null) {
            this.excludeModels = new ArrayList();
        } else {
            this.excludeModels = e.getExcludeModels();
        }

        File srcDir = Paths.get(e.getSrcRoot()).toFile();
        if (!srcDir.exists()) {
            throw new GenerationPluginException(String.format("srcRoot %s does not exist", e.getSrcRoot()));
        } else if (srcDir.isFile()) {
            throw new GenerationPluginException(String.format("srcRoot %s should be dir", e.getSrcRoot()));
        } else {
            File modelPackageDir = srcDir.toPath().resolve(this.converter.convert(e.getModelPackage())).toFile();
            if (modelPackageDir.exists() && !modelPackageDir.isFile()) {
                File generationRootDir = new File(e.getGenerationRoot());
                if (generationRootDir.exists() && generationRootDir.isFile()) {
                    throw new GenerationPluginException(String.format("srcRoot %s should be dir", generationRootDir));
                } else {
                    if (!generationRootDir.exists()) {
                        generationRootDir.mkdirs();
                    }

                    this.models = this.getModelsFromApiPackage(modelPackageDir);
                    if (e.getGenerationPackages() != null && e.getGenerationTemplates() != null) {
                        Iterator var5 = this.models.iterator();

                        while(var5.hasNext()) {
                            String m = (String)var5.next();
                            List<String> packages = e.getGenerationPackages();
                            List<String> templates = e.getGenerationTemplates();

                            for(int i = 0; i < packages.size(); ++i) {
                                File generationTemplateSrc = new File((String)templates.get(i));
                                if (!generationTemplateSrc.exists()) {
                                    throw new GenerationPluginException(String.format("generation template file %s does not exist", generationTemplateSrc));
                                }

                                File generationDir = generationRootDir.toPath().resolve(this.converter.convert((String)packages.get(i))).toFile();
                                if (!generationDir.exists()) {
                                    generationDir.mkdirs();
                                }

                                File generationFileOut = generationDir.toPath().resolve(m + generationTemplateSrc.getName() + ".java").toFile();
                                if (!generationFileOut.exists()) {
                                    try {
                                        generationFileOut.createNewFile();
                                    } catch (IOException var15) {
                                        this.logger.warning(String.format("Cannot create file %s", generationFileOut));
                                    }
                                }

                                this.generationItems.add(new GenerationItem(m, generationFileOut, generationTemplateSrc));
                            }
                        }
                    }

                    if (e.getGenerationConcatOuts() != null && e.getGenerationConcatTemplates() != null) {
                        List<String> dirs = e.getGenerationConcatOuts();
                        List<String> templates = e.getGenerationConcatTemplates();

                        for(int i = 0; i < dirs.size(); ++i) {
                            File templateSrc = new File((String)templates.get(i));
                            if (!templateSrc.exists()) {
                                throw new GenerationPluginException("template source is not exist!");
                            }

                            File out = new File((String)dirs.get(i));
                            if (!out.exists()) {
                                try {
                                    out.createNewFile();
                                } catch (IOException var14) {
                                    throw new GenerationPluginException(String.format("Cannot create out file in %s", out));
                                }
                            }

                            this.generationConcatItems.add(new GenerationConcatItem(this.models, out, templateSrc));
                        }
                    }

                }
            } else {
                throw new GenerationPluginException(String.format("modelPackage %s does not exist", e.getModelPackage()));
            }
        }
    }

    private List<String> getModelsFromApiPackage(File dir) {
        List<String> apiClasses = new ArrayList();
        File[] var3 = dir.listFiles();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];

            try {
                String modelName = this.getModelName(file);
                if (!this.excludeModels.contains(modelName)) {
                    apiClasses.add(modelName);
                }
            } catch (IllegalArgumentException var8) {
            }
        }

        return apiClasses;
    }

    private String getModelName(File file) {
        String fileName = file.getName();
        if (!fileName.substring(fileName.length() - 5).equals(".java")) {
            throw new IllegalArgumentException();
        } else {
            Pattern pattern = Pattern.compile("^(.*).java$");
            Matcher matcher = pattern.matcher(fileName);
            if (!matcher.matches()) {
                throw new IllegalArgumentException();
            } else {
                return matcher.group(1);
            }
        }
    }

    public List<GenerationItem> getGenerationItems() {
        return this.generationItems;
    }

    public List<String> getModels() {
        return this.models;
    }

    public List<GenerationConcatItem> getGenerationConcatItems() {
        return this.generationConcatItems;
    }
}
