package com.jvr.build.info.api;

import java.io.Serializable;

/**
 * Expresses dependency on another project
 */
public class Module implements Serializable {

    private String name = null;
    private ProjectRoot module = null;

    public Module() {
    }

    public Module(String name, ProjectRoot module) {
        this.name = name;
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectRoot getModule() {
        return module;
    }

    public void setModule(ProjectRoot module) {
        this.module = module;
    }
}
