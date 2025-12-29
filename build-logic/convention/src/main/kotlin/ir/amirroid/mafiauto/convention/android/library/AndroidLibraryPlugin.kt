package ir.amirroid.mafiauto.convention.android.library

import ir.amirroid.mafiauto.convention.findPluginId
import ir.amirroid.mafiauto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyRequiredPlugins()
        target.configureExtensions()
    }

    private fun Project.applyRequiredPlugins() {
        pluginManager.apply(libs.findPluginId("androidLibrary"))
    }

    private fun Project.configureExtensions() {
        extensions.configure<KotlinMultiplatformExtension>(::configureAndroidLibraryPlugins)
    }
}