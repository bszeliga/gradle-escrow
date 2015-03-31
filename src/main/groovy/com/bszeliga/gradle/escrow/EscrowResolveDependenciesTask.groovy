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

    def PomsModel pomsModel

    @TaskAction
    def resolve() {

        final def Action<ResolvableDependencies> afterResolver = getAfterResolver(pomsModel)

        project.configurations.all(installResolver.curry(afterResolver))
        project.configurations.all { it.incoming.resolutionResult.allComponents }
    }

    def installResolver = { Action<ResolvableDependencies> afterResolver, Configuration configuration ->
        configuration.incoming.afterResolve(afterResolver)
    }


    def getAfterResolver(PomsModel pomsModel) {
        new EscrowAfterResolver(project: project, pomsModel: pomsModel)
    }

    static class EscrowAfterResolver implements Action<ResolvableDependencies> {

        def Project project
        def PomsModel pomsModel

        @Override
        void execute(ResolvableDependencies resolvableDependencies) {
            resolvableDependencies.getResolutionResult().getAllComponents().each addToModel
        }

        def addToModel = { ResolvedComponentResult result ->
            if (resultIsNotLocalProject(result)) {
                pomsModel.add(result)
            }
        }

        boolean resultIsNotLocalProject(ResolvedComponentResult resolvedComponentResult) {
            return project.rootProject.group != resolvedComponentResult.moduleVersion.group &&
                    project.rootProject.name != resolvedComponentResult.moduleVersion.name
        }
    }
}
