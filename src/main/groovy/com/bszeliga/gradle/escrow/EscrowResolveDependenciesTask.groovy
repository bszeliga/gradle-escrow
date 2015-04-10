package com.bszeliga.gradle.escrow

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.artifacts.result.ResolvedComponentResult
import org.gradle.api.tasks.TaskAction

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/30/2015 <BR/>
 * Time: 10:10 PM <BR/>
 */
class EscrowResolveDependenciesTask extends DefaultTask {

    def List<PomDependency> poms

    @TaskAction
    def resolve() {

        final def Action<ResolvableDependencies> afterResolver = getAfterResolver(poms)

        project.configurations.all(installResolver.curry(afterResolver))
        project.configurations.all { it.incoming.resolutionResult.allComponents }
    }

    def installResolver = { Action<ResolvableDependencies> afterResolver, Configuration configuration ->
        configuration.incoming.afterResolve(afterResolver)
    }


    def getAfterResolver(List<PomDependency> poms) {
        new EscrowAfterResolver(project: project, poms:poms)
    }

    static class EscrowAfterResolver implements Action<ResolvableDependencies> {

        def Project project
        def List<PomDependency> poms

        @Override
        void execute(ResolvableDependencies resolvableDependencies) {
            resolvableDependencies.getResolutionResult().getAllComponents().each addToModel
        }

        def addToModel = { ResolvedComponentResult result ->
            if (resultIsNotLocalProject(result)) {
                poms << (new PomDependencyBuilder(project: project,
                        groupId:result.moduleVersion.group,
                        module:result.moduleVersion.name,
                        version:result.moduleVersion.version).build())
            }
        }

        boolean resultIsNotLocalProject(ResolvedComponentResult resolvedComponentResult) {
            return project.rootProject.group != resolvedComponentResult.moduleVersion.group &&
                    project.rootProject.name != resolvedComponentResult.moduleVersion.name
        }
    }
}
