package com.bszeliga.gradle.escrow

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/31/2015 <BR/>
 * Time: 12:23 AM <BR/>
 */
class EscrowClean extends DefaultTask {

    @TaskAction
    def clean() {
        def String baseDirString = ".m2"
        project.delete(project.relativePath(baseDirString))
    }
}
