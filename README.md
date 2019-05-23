# crudrepositorygenerator
Gradle plugin for generation code by models' classes

## How to use
### Gradle
```groovy
apply plugin: 'space.borisgk.crudgeneration'

buildscript {
    repositories {
        maven {
            url 'https://raw.github.com/borisgk98/crudrepositorygenerator/mvn-repo'
        }
    }
    dependencies {
        classpath 'space.borisgk:crudgeneration:0.1'
    }
}

crudGenerationSetting {
    srcRoot = "$projectDir/src/main/java"
    modelPackage = "$projectDir/src/main/com/example/model"
    generationRoot = "$projectDir/src/main/java"
    yamlPathTemplateSrc = "$projectDir/path/to/openapi3/template/for/paths/generation"
    yamlGenerationOut = "$projectDir/path/generation/out.yaml"
    generationPackages = [
            "com.example.controller"
    ]
    generationTemplates = [
            "$projectDir/src/main/resources/templates/Controller".toString(),
    ]
    excludeModels = [
            "OrderStatus"
    ]
}
```
