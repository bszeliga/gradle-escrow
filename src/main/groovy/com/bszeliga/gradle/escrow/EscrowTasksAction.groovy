package com.bszeliga.gradle.escrow

import org.gradle.api.Action
import org.gradle.api.Project

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/30/2015 <BR/>
 * Time: 9:56 PM <BR/>
 */
class EscrowTasksAction implements Action<Project> {

    public interface EscrowTasksConstants {

    }

    @Override
    void execute(Project project) {
        final def tasks = project.tasks
        final def List<PomDependency> poms = []

        final def EscrowResolveDependenciesTask resolveTask = tasks.create("escrowResolveDependenciesTask", EscrowResolveDependenciesTask.class)
        final def EscrowResolvePomsTask pomsTask = tasks.create("escrowResolvePomsTask", EscrowResolvePomsTask.class)
        final def EscrowCopyTask copyTask = tasks.create("escrowCopyTask", EscrowCopyTask.class)
        final def escrowTask = tasks.create("escrow", EscrowTask.class)
        final def escrowClean = tasks.create("escrowClean", EscrowClean.class)


        resolveTask.setPoms(poms)

        pomsTask.dependsOn(resolveTask)
        pomsTask.setPoms(poms)


        copyTask.dependsOn(pomsTask, resolveTask)
        copyTask.setPomsConfigurationTask(pomsTask)

        escrowTask.dependsOn(copyTask, pomsTask, resolveTask)
    }
}
