package me.tuple.playstore

import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import me.tuple.ext.moveFile
import me.tuple.version.getVersionJSON
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Created by Gokul.
 */
open class PublishApkTask : DefaultTask() {

    lateinit var publishConfig: PublishConfig
    lateinit var variant: ApplicationVariant

    @TaskAction
    fun publishApk() {
        publishConfig.packageName = variant.applicationId
        if (!File(publishConfig.jsonKeyPath).exists()) {
            if (File(project.projectDir.path + "/" + publishConfig.jsonKeyPath).exists()) {
                publishConfig.jsonKeyPath = File(project.projectDir.path + "/" + publishConfig.jsonKeyPath).path
            }
        }
        val versionJson = getVersionJSON(project)
        val versionCodes = ArrayList<Int>()

        publishConfig.mappingFile = variant.mappingFile?.path

        variant.outputs.forEach {
            if (it is ApkVariantOutput) {
                publishConfig.apkFilePath = it.outputFile.path
                moveFile(it.outputFile, File(project.rootDir, versionJson.getString("milestone_dir") + "/" + it.name + "/" + variant.versionName + "/" + "apk-${it.name}.apk"))
                logger.lifecycle(publishConfig.toString())
                versionCodes.add(PublishApk.publish(publishConfig).versionCode)
            }
        }

        variant.mappingFile?.apply {
            if (exists()) {
                moveFile(this, File(project.rootDir, versionJson.getString("milestone_dir") + "/" + variant.name + "/" + variant.versionName + "/" + "mapping.txt"))
            }
        }

        logger.lifecycle("Apk versions - " + versionCodes.toString() + " Published")
    }
}