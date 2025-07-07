package ir.amirroid.mafiauto.git

import org.gradle.api.Project
import org.eclipse.jgit.api.Git

import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

class GitVersionExtension(project: Project) {
    val version = project.provider {
        resolveGitVersion(project.rootDir)
    }

    private fun resolveGitVersion(rootDir: File): String {
        return runCatching {
            val repository = FileRepositoryBuilder()
                .setGitDir(File(rootDir, ".git"))
                .readEnvironment()
                .findGitDir()
                .build()

            val git = Git(repository)
            val tag = git.describe().setTags(true).call()
            if (!tag.isNullOrBlank()) {
                return extractVersionFromTag(tag)
            }

            val branch = repository.fullBranch ?: return "dev"
            sanitizeBranch(branch)
        }.getOrDefault("dev")
    }

    private fun extractVersionFromTag(tag: String): String {
        return Regex("""\d+(\.\d+)*""")
            .find(tag)
            ?.value
            ?: tag
    }

    private fun sanitizeBranch(branch: String): String {
        return branch
            .removePrefix("refs/heads/")
            .replace("/", "-")
            .replace("[^a-zA-Z0-9.-]".toRegex(), "-")
    }
}