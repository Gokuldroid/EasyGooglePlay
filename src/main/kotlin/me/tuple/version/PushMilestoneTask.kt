package me.tuple.version

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File


/**
 * Created by Gokul.
 */

open class PushMilestoneTask : DefaultTask() {

    @TaskAction
    fun pushMileStone() {
        val repositoryBuilder = FileRepositoryBuilder()
        val repository = repositoryBuilder.setGitDir(File(project.rootDir, ".git"))
                .readEnvironment()
                .findGitDir()
                .setMustExist(true)
                .build()

        val git = Git(repository)
        git.add().addFilepattern(".").call()
        val versionJSON = getVersionJSON(project)
        val versionName = versionJSON.getString("version_name")
        git.commit().apply {
            message = "Version : $versionName pushed"
            call()
        }
        git.tag().apply {
            name = "v" + versionName
            call()
        }
    }
}