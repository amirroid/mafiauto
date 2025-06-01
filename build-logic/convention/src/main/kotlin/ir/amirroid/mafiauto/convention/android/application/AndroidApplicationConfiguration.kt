package ir.amirroid.mafiauto.convention.android.application

import com.android.build.api.dsl.ApplicationExtension
import ir.amirroid.mafiauto.convention.PACKAGE_NAME
import ir.amirroid.mafiauto.convention.RELEASE_IS_MINIFY_ENABLED
import ir.amirroid.mafiauto.convention.androidLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroidApplicationPlugins(
    extensions: ApplicationExtension
) {
    fun String.versionInt(): Int =
        androidLibs.findVersion(this).get().requiredVersion.toInt()

    extensions.apply {
        namespace = PACKAGE_NAME
        compileSdk = "compileSdk".versionInt()

        defaultConfig {
            minSdk = "minSdk".versionInt()
            targetSdk = "targetSdk".versionInt()
        }

        packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

        buildTypes.named("release") {
            isMinifyEnabled = RELEASE_IS_MINIFY_ENABLED
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}
