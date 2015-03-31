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

    def PomsModel pomsModel
    def Configuration pomsConfiguration

    @TaskAction
    def resolve() {

        println("STARTING POMS")
        println(pomsModel.poms)
        final def List<Dependency> dependencies = pomsModel.poms.collect convertToDependencies
        pomsConfiguration = project.configurations.detachedConfiguration(dependencies.toArray(new Dependency[dependencies.size()]))
    }

    def convertToDependencies = { project.dependencies.create(it) }
}
