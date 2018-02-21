package me.tuple

import com.android.build.gradle.AppExtension
import me.tuple.ext.createTask
import me.tuple.playstore.PublishApkTask
import me.tuple.playstore.PublishConfig
import me.tuple.version.BumpVersionTask
import me.tuple.version.PushMilestoneTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Gokul.
 */

class EasyGooglePlayPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val easyGooglePlayExt = project.extensions.create("easyGooglePlay", PublishConfig::class.java)


        val android = project.extensions.getByType(AppExtension::class.java)

        project.logger.lifecycle("Application variants " + android.applicationVariants.size)
        android.applicationVariants.whenObjectAdded {
            if (it.buildType.isDebuggable || !it.isSigningReady) {
                return@whenObjectAdded
            }
            val publishApkTaskName = "publish${it.name.capitalize()}Apk"
            val publishTask = project.createTask<PublishApkTask>(publishApkTaskName)
            publishTask.publishConfig = easyGooglePlayExt
            publishTask.variant = it
            publishTask.dependsOn(it.assemble)
        }

        val bumpMajorVersionTask = project.createTask<BumpVersionTask>("bumpMajorVersion")
        bumpMajorVersionTask.isMajorVersion = true


        val bumpMinorVersionTask = project.createTask<BumpVersionTask>("bumpMinorVersion")
        bumpMinorVersionTask.isMajorVersion = false

        val pushMileStoneTask = project.createTask<PushMilestoneTask>("pushMilestone")
    }
}