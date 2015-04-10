package com.bszeliga.gradle.escrow

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.TaskAction

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/27/2015 <BR/>
 * Time: 2:02 PM <BR/>
 */
class EscrowCopyTask extends DefaultTask {

    def String baseDirString = ".m2"
    def EscrowResolvePomsTask pomsConfigurationTask

    @TaskAction
    def copy() {
//        baseDirString = project.file(project.extensions.escrow.dir)
        project.configurations.all copyConfiguration
        pomsConfigurationTask.getPomsConfiguration().each copyConfiguration
    }

    def copyConfiguration = { Configuration configuration ->
        configuration.resolvedConfiguration.resolvedArtifacts.each copyResolvedArtifact
    }

    def copyResolvedArtifact = { ResolvedArtifact artifact ->
        final def File file = buildFile(artifact.moduleVersion.id.group,
                artifact.moduleVersion.id.name,
                artifact.moduleVersion.id.version)

        project.mkdir(file)
        project.copy { CopySpec spec ->
            spec.from(artifact.file)
            spec.into(file)
        }
    }

    def File buildFile(String group, String name, String version) {
        project.mkdir([baseDirString,
                       group.replace(".", File.separator),
                       name,
                       version].join(File.separator))

    }
}
