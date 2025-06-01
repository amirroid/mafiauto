package ir.amirroid.mafiauto.convention.koin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            target.extensions.configure<KotlinMultiplatformExtension>(::configureKoinPlugins)
        }
    }
}