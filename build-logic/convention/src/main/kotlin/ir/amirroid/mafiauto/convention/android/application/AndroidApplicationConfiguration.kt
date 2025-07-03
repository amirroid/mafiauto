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

        configureSigningIfAvailable(this)

        packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

        buildTypes.named("release") {
            isMinifyEnabled = RELEASE_IS_MINIFY_ENABLED
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        lint {
            disable.add("NullSafeMutableLiveData")
        }
    }
}

private fun Project.configureSigningIfAvailable(android: ApplicationExtension) {
    val signingProps = listOf(
        "signing.store.file",
        "signing.store.password",
        "signing.key.alias",
        "signing.key.password"
    )

    val hasSigningConfig = signingProps.all { hasProperty(it) }

    if (hasSigningConfig) {
        android.signingConfigs {
            create("release") {
                storeFile = file(property("signing.store.file") as String)
                storePassword = property("signing.store.password") as String
                keyAlias = property("signing.key.alias") as String
                keyPassword = property("signing.key.password") as String
            }
        }

        android.buildTypes.named("release") {
            signingConfig = android.signingConfigs.getByName("release")
        }
    }
}