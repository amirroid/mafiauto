package ir.amirroid.mafiauto.convention.android.library

import com.android.build.api.dsl.LibraryExtension
import ir.amirroid.mafiauto.convention.PACKAGE_NAME
import ir.amirroid.mafiauto.convention.androidLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroidLibraryPlugins(
    extensions: LibraryExtension
) {
    fun String.versionInt(): Int =
        androidLibs.findVersion(this).get().requiredVersion.toInt()

    extensions.apply {
        namespace = PACKAGE_NAME
        compileSdk = "compileSdk".versionInt()

        defaultConfig {
            minSdk = "minSdk".versionInt()
        }

        packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        lint {
            disable.add("NullSafeMutableLiveData")
        }
    }
}
