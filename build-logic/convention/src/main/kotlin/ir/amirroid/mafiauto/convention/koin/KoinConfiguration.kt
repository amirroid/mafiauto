package ir.amirroid.mafiauto.convention.koin

import ir.amirroid.mafiauto.convention.androidMain
import ir.amirroid.mafiauto.convention.commonMain
import ir.amirroid.mafiauto.convention.libs
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun Project.configureKoinPlugins(
    extensions: KotlinMultiplatformExtension
) {
    extensions.apply {
        configureCommonMain(sourceSets)
        configureAndroidMain(sourceSets)
    }
}


private fun Project.configureCommonMain(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) {
    val commonMain = sourceSets.commonMain
    commonMain.dependencies {
        implementation(project.dependencies.platform(libs.findLibrary("koin-bom").get()))
        implementation(libs.findLibrary("koin-core").get())
        implementation(libs.findLibrary("koin-compose").get())
        implementation(libs.findLibrary("koin-compose-viewmodel").get())
        implementation(libs.findLibrary("koin-compose-viewmodel-navigation").get())
    }
}

private fun Project.configureAndroidMain(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) {
    val androidMain = sourceSets.androidMain
    androidMain.dependencies {
        implementation(libs.findLibrary("koin-android").get())
    }
}