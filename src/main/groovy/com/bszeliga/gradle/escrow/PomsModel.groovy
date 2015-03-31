package com.bszeliga.gradle.escrow

import org.gradle.api.artifacts.result.ResolvedComponentResult

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/30/2015 <BR/>
 * Time: 10:36 PM <BR/>
 */
class PomsModel {

    def poms = []

    def add(ResolvedComponentResult resolvedComponentResult) {
        add(resolvedComponentResult.moduleVersion.group, resolvedComponentResult.moduleVersion.name, resolvedComponentResult.moduleVersion.version)
    }

    def add(String group, String name, String version) {
        poms << format(group, name, version)
    }

    def String format(String group, String name, String version) {
        return group + ":" + name + ":" + version + "@pom"
    }
}
