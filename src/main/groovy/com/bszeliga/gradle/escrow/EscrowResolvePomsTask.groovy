package com.bszeliga.gradle.escrow

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.api.tasks.TaskAction

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/30/2015 <BR/>
 * Time: 10:10 PM <BR/>
 */
class EscrowResolvePomsTask extends DefaultTask {

    def List<PomDependency> poms
    def List<Configuration> pomsConfiguration = []

    @TaskAction
    def resolve() {
        poms += resolve(poms)
    }

    def resolve(List<PomDependency> poms) {
        def configuration = buildConfiguration(poms)

        def List<PomDependency> parentPoms = []
        configuration.files.each {
            def parsed = new XmlSlurper().parse(it)
            if (parsed.parent != "") {
                parentPoms << new PomDependencyBuilder(project: project,
                        groupId: parsed.parent.groupId.text(),
                        module: parsed.parent.artifactId.text(),
                        version: parsed.parent.version.text()).build()
            }
        }

        if (!parentPoms.empty) {
            parentPoms += resolve(parentPoms)
        }

        pomsConfiguration << configuration

        return parentPoms

    }

    def buildConfiguration(List<Dependency> poms) {
        return project.configurations.detachedConfiguration(poms.toArray(new Dependency[poms.size()]))
    }
}
