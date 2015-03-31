package com.bszeliga.gradle.escrow

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/27/2015 <BR/>
 * Time: 2:00 PM <BR/>
 */
class EscrowPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        new EscrowTasksAction().execute(project)
    }

}
