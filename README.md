# project-info-plugins 
[![Build Status](https://travis-ci.org/JV-ration/project-info-plugins.svg?branch=master)](https://travis-ci.org/JV-ration/project-info-plugins) 

Plugins for build tools like Maven and Gradle to generate unified information about the projects

![project-info-plugins.jpg](https://s10.postimg.org/4ouor9awp/project_info_plugins.jpg)

## Using from command line

If you'd like to use snapshot versions of the plugins, install them in your local maven repository by `./gradlew build install`

### Maven

Execute command below on any maven project you have
 
```
mvn com.jv-ration.maven.plugins:project-info-maven-plugin:<PLUGIN_VERSION>:info -DoutputFile=info.json
```

The generated JSON will contains project information and can be loaded using classes from `project-info-api` module

### Gradle

Apply the plugin to your Gradle root project
```
buildscript {
    repositories {
        maven {
            url 'https://repo.gradle.org/gradle/libs-releases-local'
        }
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath 'com.jv-ration.gradle.plugins:project-info-gradle-plugin:<PLUGIN_VERSION>'
    }
}

allprojects {
    apply plugin: com.jvr.gradle.plugins.info.ProjectInfoPlugin
}
```

You can also apply it without modifying `build.gradle` files. Example of the initialization script is `project-info-gradle-test/src/test/resources/project-info-task.gradle` file.
Change to `project-info-gradle-test/build/resources/test/multi/` directory and execute

```
gradle --init-script=../project-info-task.gradle project-info
```

## Using from code

If you'd like to use snapshot versions of the plugins, install them in your local maven repository by `./gradlew build install`

### Maven

Invoking Maven to generate JSON file with Project description

```
Properties buildProperties = new Properties();
buildProperties.setProperty("outputFile", "info.json");

List<String> goals = Collections.singletonList("com.jv-ration.maven.plugins:project-info-maven-plugin:<PLUGIN_VERSION>:info");

InvocationRequest request = new DefaultInvocationRequest();
request.setBaseDirectory(new File("/projects/maven-sample").setGoals(goals).setProperties(buildProperties).setInteractive(false);

Invoker invoker = new DefaultInvoker();
invoker.execute(request);
```

Using project information in your code
```
String json = FileUtils.readFileToString(new File("info.json"));
ProjectRoot project = ProjectJson.fromJson(json);
```

### Gradle

Example of using Gradle plugin from the code is in `project-info-gradle-test/src/test/java/com/jvr/gradle/invoker/GradleProjectInfoRetrieverTest.java` test class.
`GradleProjectInfoRetriever` uses bundled init script to register new Project Info model and then builds it.

```
GradleProjectInfoRetriever retriever = new GradleProjectInfoRetriever();
// provide path to the project location
ProjectRoot projectRoot = retriever.retrieveProjectInfo(new File("/projects/gradle-sample"));
```

## Releases

[ ![Download](https://api.bintray.com/packages/jv-ration/maven/project-info-plugins/images/download.svg) ](https://bintray.com/jv-ration/maven/project-info-plugins/_latestVersion)

Currently the project releases are deployed to [Bintray](https://bintray.com/jv-ration/maven/project-info-plugins). 
Open this page an lookup the latest release

By adding the following to your `pom.xml` you can use Maven plugin without building it locally

```
<pluginRepositories>
    <pluginRepository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>bintray-jv-ration-maven</id>
        <name>bintray-plugins</name>
        <url>http://dl.bintray.com/jv-ration/maven</url>
    </pluginRepository>
</pluginRepositories>
```

Execute this from the command line

```
mvn com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.8:info -q
```