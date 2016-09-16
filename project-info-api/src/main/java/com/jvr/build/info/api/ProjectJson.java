package com.jvr.build.info.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility methods for writing Project into JSON and reading it back
 */
public class ProjectJson {

    private ProjectJson() {
        // disable invocation of constructor
    }

    public static String toString(ProjectRoot project, boolean pretty) {
        GsonBuilder builder = new GsonBuilder();
        if (pretty) {
            builder.setPrettyPrinting();
        }
        Gson gson = builder.create();
        return gson.toJson(project);
    }

    public static ProjectRoot fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ProjectRoot.class);
    }

}
