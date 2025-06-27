package ir.amirroid.mafiauto.convention.compose

import ir.amirroid.mafiauto.convention.androidMain
import ir.amirroid.mafiauto.convention.commonMain
import ir.amirroid.mafiauto.convention.composeDependencies
import ir.amirroid.mafiauto.convention.implementIfNotSelf
import ir.amirroid.mafiauto.convention.libs
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun Project.configureComposeMultiplatformPlugins(
    extensions: KotlinMultiplatformExtension
) {
    extensions.apply {
        configureCommonMain(sourceSets)
        configureAndroidMain(sourceSets)

    }
}


private fun Project.configureCommonMain(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) {
    val commonMain = sourceSets.commonMain
    val dependencies = composeDependencies
    commonMain.dependencies {
        implementation(dependencies.runtime)
        implementation(dependencies.foundation)
        implementation(dependencies.ui)
        implementation(dependencies.components.resources)

        implementation(libs.findLibrary("androidx-lifecycle-viewmodel").get())
        implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
        implementation(libs.findLibrary("composeIcons-evaIcons").get())

        val resources = ":core:resources"
        val designSystem = ":core:design-system"
        val commonCompose = ":core:common:compose"

        val avoidAddsList = listOf(resources, designSystem)
        if (project.path !in avoidAddsList) {
            implementIfNotSelf(commonCompose)
            implementIfNotSelf(designSystem)
        }
        implementIfNotSelf(resources)
    }
}

private fun Project.configureAndroidMain(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) {
    val androidMain = sourceSets.androidMain
    androidMain.dependencies {
        implementation(composeDependencies.preview)
        implementation(libs.findLibrary("androidx-activity.compose").get())
    }
}