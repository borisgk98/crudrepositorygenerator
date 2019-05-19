package space.borisgk.gradletestplugin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GenerationPluginExtension {
    private String srcRoot;
    private String generationPackage;
    private String srcPackage;
    private String generationRoot;
}
