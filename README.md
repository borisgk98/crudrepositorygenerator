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
        classpath 'space.borisgk:crudgeneration:0.2'
        classpath 'org.antlr:stringtemplate:3.2'
    }
}

crudGenerationSetting {
    srcRoot = "$projectDir/src/main/java"
    modelPackage = "$projectDir/src/main/com/example/model"
    generationRoot = "$projectDir/src/main/java"
    generationPackages = [
            "com.example.controller"
    ]
    generationTemplates = [
            "$projectDir/src/main/resources/templates/Controller".toString(),
    ]
    generationConcatOuts = [
            "$projectDir/src/main/resources/paths.yaml".toString()
    ]
    generationConcatTemplates = [
            "$projectDir/src/main/resources/templates/Path".toString()
    ]
    excludeModels = [
            "OrderStatus"
    ]
}
```
## Writing a templates
For generation code by templates this plugin use [Atlassian String template ](https://theantlrguy.atlassian.net/wiki/spaces/ST/pages/1409093/Introduction)

Allows params:
- $model$ - lowercase model name
- $Model$ - model name read from model class file name 
- $Modelufl$ - model with uppercase first later and lowercase another laters

Example:
- UserDetails.java - source model file from modelPackage
- $model$ - userdetails
- $Model$ - UserDetails
- $Modelufl$ - Userdetails
