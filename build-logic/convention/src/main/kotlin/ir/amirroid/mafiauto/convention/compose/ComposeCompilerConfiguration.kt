package ir.amirroid.mafiauto.convention.compose

import org.gradle.api.Project
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

fun Project.configureComposeCompiler(extension: ComposeCompilerGradlePluginExtension) {
    extension.apply {
        reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
        metricsDestination.set(layout.buildDirectory.dir("compose_compiler"))
    }
}