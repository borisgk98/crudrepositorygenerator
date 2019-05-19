package space.borisgk.gradletestplugin;

public class JavaClass {
    public String name;
    public String pack;

    public JavaClass(String name, String pack) {
        this.name = name;
        this.pack = pack;
    }

    public String toString() {
        return String.format("package %s;public class %s {}", this.pack, this.name);
    }
}
