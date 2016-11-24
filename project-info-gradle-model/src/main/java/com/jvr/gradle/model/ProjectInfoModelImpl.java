package com.jvr.gradle.model;

import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;

public class ProjectInfoModelImpl implements ProjectInfoModel {

    private final ProjectRoot root;

    public ProjectInfoModelImpl(ProjectRoot root) {
        this.root = root;
    }

    @Override
    public String getRootProjectJson() {
        return ProjectJson.toString(root, false);
    }

    public ProjectRoot getRoot() {
        return root;
    }

}
