package ir.amirroid.mafiauto.convention.compose

import ir.amirroid.mafiauto.convention.libs
import ir.amirroid.mafiauto.convention.findPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyRequiredPlugins()
        target.configureExtensions()
    }

    private fun Project.applyRequiredPlugins() {
        pluginManager.apply {
            apply(libs.findPluginId("local-kotlin"))
            apply(libs.findPluginId("composeMultiplatform"))
            apply(libs.findPluginId("composeCompiler"))
        }
    }

    private fun Project.configureExtensions() {
        extensions.configure<KotlinMultiplatformExtension>(::configureComposeMultiplatformPlugins)
    }
}