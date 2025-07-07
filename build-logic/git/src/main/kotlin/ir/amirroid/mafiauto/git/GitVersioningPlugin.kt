package ir.amirroid.mafiauto.git

import org.gradle.api.Plugin
import org.gradle.api.Project

class GitVersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val gitVersion = GitVersionExtension(project)
        project.extensions.add("gitVersion", gitVersion)
    }
}