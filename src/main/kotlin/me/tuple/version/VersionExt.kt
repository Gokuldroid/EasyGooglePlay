package me.tuple.version

import me.tuple.ext.hasFile
import me.tuple.ext.readAsJSON
import org.gradle.api.Project
import org.json.JSONObject
import java.io.File

/**
 * Created by Gokul.
 */

fun getVersionJSON(project: Project): JSONObject {
    val rootDir = project.rootDir
    val projectDir = project.projectDir
    if (rootDir.hasFile("version.json")) {
        return File(rootDir, "version.json").readAsJSON()
    } else if (projectDir.hasFile("version.json")) {
        return File(projectDir, "version.json").readAsJSON()
    }
    throw Exception("Version file not found")
}

fun setVersionJSON(project: Project, version: JSONObject) {
    val rootDir = project.rootDir
    val projectDir = project.projectDir
    if (rootDir.hasFile("version.json")) {
        File(rootDir, "version.json").writeText(version.toString(5))
    } else if (projectDir.hasFile("version.json")) {
        File(projectDir, "version.json").writeText(version.toString(5))
    }
}