# project-info-plugins 
[![Build Status](https://travis-ci.org/JV-ration/project-info-plugins.svg?branch=master)](https://travis-ci.org/JV-ration/project-info-plugins) 

Plugins for build tools like Maven and Gradle to generate unified information about the projects

![project-info-plugins.jpg](https://s10.postimg.org/4ouor9awp/project_info_plugins.jpg)

## Using from command line

### Maven

After building the plugins locally and installing them in local maven repository `./gradlew build install`, execute command 
below on any maven project you have
 
```
mvn com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.9-SNAPSHOT:info -DoutputFile=info.json
```

The generated JSON will contains project information and can be loaded using classes 
from `project-info-api` module

### Gradle

Example of using Gradle plugin from CLI is `project-info-gradle-test/src/test/resources/project-info-task.gradle` file.
After building the project with `./gradlew build` change to `project-info-gradle-test/src/test/resources/multi` directory and
execute

```
gradle --init-script=../project-info-task.gradle project-info
```

To apply the plugin to your project
* build and install this project in local Maven repo by `./gradlew build install`
* copy `project-info-task.gradle` to a convenient location;
* modify it to remove files based Maven repository;
* invoke Gradle on your project providing modified initialization script 

## Using from code

### Maven

Invoking Maven to generate JSON file with Project description

```
Properties buildProperties = new Properties();
buildProperties.setProperty("outputFile", "info.json");

List<String> goals = Collections.singletonList("com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.8:info");

InvocationRequest request = new DefaultInvocationRequest();
request.setGoals(goals).setProperties(buildProperties).setInteractive(false);

Invoker invoker = new DefaultInvoker();
invoker.execute(request);
```

Using project information in your code. This code is the same for Maven and Gradle projects
```
String json = FileUtils.readFileToString(new File("info.json"));
ProjectRoot project = ProjectJson.fromJson(json);
```

### Gradle

Example of using Gradle plugin from the code is in `project-info-gradle-test/src/test/java/com/jvr/gradle/invoker/GradleProjectInfoRetrieverTest.java` test class.
`GradleProjectInfoRetriever` uses bundled init script to register new Project Info model and then builds it.

In order to move this example to your code
* build and install this project in local Maven repo by `./gradlew build install`
* modify the test class to stop providing additional maven repository and to match locations of the projects to analyze 

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