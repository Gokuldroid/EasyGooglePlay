package me.tuple.ext

import org.gradle.api.DefaultTask
import org.gradle.api.Project

inline fun <reified T : DefaultTask> Project.createTask(taskName: String): T {
    val task = tasks.create(taskName, T::class.java)
    task.group = "EasyGooglePlay"
    return task
}