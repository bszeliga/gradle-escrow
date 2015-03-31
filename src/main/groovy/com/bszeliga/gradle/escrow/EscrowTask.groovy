package com.bszeliga.gradle.escrow

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.specs.Specs
import org.gradle.api.tasks.TaskAction

/**
 * Created with IntelliJ IDEA. <BR/>
 * User: bszeliga <BR/>
 * Date: 3/27/2015 <BR/>
 * Time: 2:02 PM <BR/>
 */
class EscrowTask extends DefaultTask {

    @TaskAction
    def escrow() {
//        project.configurations.getByName("runtime").resolvedConfiguration.getFiles(new Spec<Dependency>() {
//            @Override
//            boolean isSatisfiedBy(Dependency element) {
//                return true
//            }
//        }).each {println(it)}

//        project.configurations.getByName("runtime").allArtifacts.files.each {print(it)}

//        project.configurations.getByName("runtime").allDependencies.each {println(it)}

//        println("first level module dependencies")
//        project.configurations.getByName("runtime").resolvedConfiguration.firstLevelModuleDependencies.each { println(it.allModuleArtifacts) }
//
//        println("component selection")
//        project.configurations.getByName("runtime").resolutionStrategy.componentSelection
//
//        project.dependencies.createArtifactResolutionQuery().withArtifacts(JvmLibrary.class, Library.class).execute().components.each {println(it)}
//
//        println("it files")
//        project.configurations.getByName("runtime").resolvedConfiguration.getFiles (new Spec<Dependency>() {
//            @Override
//            boolean isSatisfiedBy(Dependency element) {
//                return true
//            }
//        }).each {println(it)}
//
//        println("resovled artifacts")
//        project.configurations.getByName("runtime").resolvedConfiguration.resolvedArtifacts.each {println(it.file)}
//
//        println("maven lcocal properties")
//        println(project.repositories.mavenLocal().properties)
//
//        println("artifact urls")
//        project.repositories.mavenLocal().artifactUrls.each {println(it)}
//
//        project.dependencies.components.eachComponent {println(it)}


        project.configurations.all {
            Configuration it ->
                println(it.name)
                it.incoming.afterResolve() {
                    ResolvableDependencies resolvableDependencies ->
                        println("after resolve")
                        println("resolvableDependencies" + resolvableDependencies.toString())

                        resolvableDependencies.getResolutionResult().getAllComponents().each {
                            println("INSIDE")
                            println(it.moduleVersion.group + ":" + it.moduleVersion.name + " : " + it.moduleVersion.version)
                        }

//                        resolvableDependencies.getResolutionResult().root.dependents.each{
//                            println(it.selected.moduleVersion)
//                        }

//                        resolvableDependencies.getResolutionResult().root.dependencies.each{
//                            println(it)
//                        }
                }
        }



        def dependencies = []
        project.configurations.all {
            Configuration configuration ->
                println("incoming")
                configuration.incoming.dependencies.each {
                    println(it)
                }
                configuration.incoming.getResolutionResult().allComponents.each {
                    println("BLAH")
                }
                configuration.allDependencies.each {
                    Dependency dependency ->
                        dependencies.add(project.dependencies.create(dependency.group + ":" + dependency.name + ":" + dependency.version + "@pom"))
                }
        }

        println(dependencies)
        def poms = project.configurations.detachedConfiguration(dependencies.toArray(new Dependency[dependencies.size()]))
        poms.files(Specs.SATISFIES_ALL).each { println(it.absolutePath) }

//        println("lenient")
//        project.configurations.getByName("runtime").resolvedConfiguration.lenientConfiguration.getArtifacts(Specs.SATISFIES_ALL).each{ResolvedArtifact it ->
//            println()
//            print(it.extension)
//            print(" ")
//            print(it.name)
//            print(" ")
//            print(it.classifier)
//            print(" ")
//            print(it.file)
//        }
    }
}
