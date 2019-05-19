package space.borisgk.gradletestplugin.util;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PackageToPathConverter implements Converter<String, Path> {
    @Override
    public Path convert(String o) {
        return Paths.get(o.replace('.', '/'));
    }
}
