package com.jvr.gradle.model.impl;

import com.jvr.build.info.api.ProjectJson;
import com.jvr.build.info.api.ProjectRoot;
import com.jvr.gradle.model.ProjectInfoModel;

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
