package me.tuple

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by gokul-4192.
 */

class EasyPlayPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val logger = project.logger

        println("Plugin applied dmo")
        project.task("publishApk").apply {
            doFirst {
                println("hello from publish task")
            }
        }
    }
}