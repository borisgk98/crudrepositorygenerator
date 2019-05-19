package space.borisgk.crudgeneration.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaClass {
    protected String packageName;
    protected String className;
    protected List<String> imports;
    protected Class source;

    public JavaClass() {
    }

    public JavaClass(Class classSource, String textSource) {
        this.source = classSource;
        imports = getImportsFromTextSource(textSource);
    }

    protected List<String> getImportsFromTextSource(String textSource) {
        List<String> res = new ArrayList<>();
        Pattern pattern = Pattern.compile("import (\\S+);");
        Matcher matcher = pattern.matcher(textSource);
        while (matcher.find()) {
            res.add(matcher.toString());
        }
        return res;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
