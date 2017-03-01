package com.jvr.gradle.model;

import org.gradle.tooling.model.Model;

import java.io.Serializable;

/**
 * This is a custom tooling model.
 * It must be assignable to {@link Model} and it must be an interface.
 */
public interface ProjectInfoModel extends Model, Serializable {

    String getRootProjectJson();

}