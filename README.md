# project-info-plugins [![Build Status](https://travis-ci.org/JV-ration/project-info-plugins.svg?branch=master)](https://travis-ci.org/JV-ration/project-info-plugins)

Plugins for build tools like Maven and Gradle to generate unified information about the projects

## Usage

Currently only Maven plugin is implemented. After building the plugins locally and installing them in 
local maven repository, execute command below on any maven project you have
 
```
mvn com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.3-SNAPSHOT:info -DoutputFile=info.json
```

The generated JSON will contains project information and can be loaded using classes 
from `project-info-api` module