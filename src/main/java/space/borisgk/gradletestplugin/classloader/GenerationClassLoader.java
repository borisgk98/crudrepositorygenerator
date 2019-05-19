package space.borisgk.gradletestplugin.classloader;

import space.borisgk.gradletestplugin.Context;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class GenerationClassLoader extends java.lang.ClassLoader {
    protected File srcDir;
    protected String packagePrefix;
    protected Logger logger = Context.logger;

    public GenerationClassLoader(File srcDir, String packagePrefix) {
        this.srcDir = srcDir;
        this.packagePrefix = packagePrefix;
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        try {
            return findLoadedClass(name);
        }
        catch (Exception e) {}
        if (!supported(name)) {
            return super.loadClass(name);
        }
        try {
            String classDir = name.replace('.', '/');
            File file = Paths.get(srcDir.getAbsolutePath(), classDir + ".class").toFile();
            InputStream input = new FileInputStream(file);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(name,
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
            throw new ClassNotFoundException("Bad class URL (class name = " + name + ")");
        } catch (IOException e) {
            throw new ClassNotFoundException("IOException while read class " + name);
        }
        catch (Exception e) {
            logger.warning(e.toString());
        }
        return null;
    }

    private boolean supported(String name) {
        return name.length() > packagePrefix.length()
                && name.substring(0, packagePrefix.length()).equals(packagePrefix);
    }
}