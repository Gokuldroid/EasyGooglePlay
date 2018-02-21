package me.tuple.version

import me.tuple.ext.increment
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject

/**
 * Created by Gokul.
 */

open class BumpVersionTask : DefaultTask() {

    var isMajorVersion: Boolean = true

    @TaskAction
    fun bumpVersion() {

        val versionJson: JSONObject = getVersionJSON(project)

        versionJson.increment("version_code")

        val versionName = versionJson.getString("version_name")

        var versionNameParts = versionName.split('.').toMutableList()

        var addOne = false

        versionNameParts = versionNameParts.reversed().toMutableList()

        versionNameParts.forEachIndexed { index, s ->
            if (index == 0) {
                if (Integer.parseInt(s) == 99 || isMajorVersion) {
                    versionNameParts[index] = "0"
                    addOne = true
                } else {
                    versionNameParts[index] = Integer.parseInt(s).inc().toString()
                }
            } else {
                if (addOne) {
                    val code = Integer.parseInt(s).inc()
                    if (code == 10) {
                        versionNameParts[index] = "0"
                        addOne = true
                    } else {
                        versionNameParts[index] = code.toString()
                        addOne = false
                    }
                }
            }
        }

        versionJson.put("version_name", versionNameParts.reversed().joinToString(separator = "."))
        setVersionJSON(project, versionJson)
    }
}