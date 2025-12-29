package ir.amirroid.mafiauto.convention.android.library

import com.android.build.api.dsl.androidLibrary
import ir.amirroid.mafiauto.convention.PACKAGE_NAME
import ir.amirroid.mafiauto.convention.androidLibs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureAndroidLibraryPlugins(
    extensions: KotlinMultiplatformExtension
) {
    fun String.versionInt(): Int =
        androidLibs.findVersion(this).get().requiredVersion.toInt()

    extensions.androidLibrary {
        namespace = PACKAGE_NAME
        compileSdk = "compileSdk".versionInt()

        withJava()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        lint {
            disable.add("NullSafeMutableLiveData")
        }
    }
}
