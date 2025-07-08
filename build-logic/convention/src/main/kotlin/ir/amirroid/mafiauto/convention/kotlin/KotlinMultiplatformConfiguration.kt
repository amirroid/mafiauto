package ir.amirroid.mafiauto.convention.kotlin

import ir.amirroid.mafiauto.convention.androidMain
import ir.amirroid.mafiauto.convention.commonMain
import ir.amirroid.mafiauto.convention.implementIfNotSelf
import ir.amirroid.mafiauto.convention.libs
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun Project.configureKotlinMultiplatformPlugins(extensions: KotlinMultiplatformExtension) {
    extensions.apply {
        applyDefaultHierarchyTemplate()
        androidTarget()
        configureIosTargets()
        configureCommonMain(sourceSets)
        configureAndroidMain(sourceSets)
    }
}

private fun KotlinMultiplatformExtension.configureIosTargets() {
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
}

private fun Project.configureCommonMain(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) {
    sourceSets.commonMain.dependencies {
        implementation(libs.findLibrary("kotlinx-serialization").get())
        implementation(libs.findLibrary("kermit").get())
        implementation(libs.findLibrary("kotlinx-collections").get())
        implementation(libs.findLibrary("coroutines-core").get())
        implementIfNotSelf(":core:common:app")
    }
}

private fun Project.configureAndroidMain(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) {
    sourceSets.androidMain.dependencies {
        implementation(libs.findLibrary("androidx-core-ktx").get())
    }
}
