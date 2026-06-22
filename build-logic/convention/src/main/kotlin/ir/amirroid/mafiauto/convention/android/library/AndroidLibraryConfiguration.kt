package ir.amirroid.mafiauto.convention.android.library

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import ir.amirroid.mafiauto.convention.androidLibs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureAndroidLibraryPlugins(
    extensions: KotlinMultiplatformExtension
) {
    fun String.versionInt(): Int =
        androidLibs.findVersion(this).get().requiredVersion.toInt()

    val namespace = path.replace(":", ".").removePrefix(".")

    val android = extensions.targets.getByName("android") as KotlinMultiplatformAndroidLibraryTarget
    android.apply {
        this.namespace = namespace
        compileSdk = "compileSdk".versionInt()

        withJava()
        withHostTest {}

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        lint {
            disable.add("NullSafeMutableLiveData")
        }
    }
}
