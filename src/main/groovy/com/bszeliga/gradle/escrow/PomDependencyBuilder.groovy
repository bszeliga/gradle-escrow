package com.bszeliga.gradle.escrow

import groovy.transform.Canonical
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 4/9/2015 <BR/>
 * Time: 10:05 PM <BR/>
 */
class PomDependencyBuilder {

    def Project project
    def String groupId
    def String module
    def String version

    def PomDependency build() {
        return new PomDependencyImpl(dependency: project.dependencies.create(formatString(groupId, module, version)))
    }

    def String formatString(String group, String module, String version) {
        [group, module, version].join(":") + "@pom"
    }

    @Canonical
    static class PomDependencyImpl implements PomDependency{
        @Delegate def Dependency dependency
    }
}
