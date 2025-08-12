package ir.amirroid.mafiauto.convention.android.application

import com.android.build.api.dsl.ApplicationExtension
import ir.amirroid.mafiauto.convention.PACKAGE_NAME
import ir.amirroid.mafiauto.convention.RELEASE_IS_MINIFY_ENABLED
import ir.amirroid.mafiauto.convention.androidLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.util.Properties

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
            versionName = rootProject.version.toString()
            versionCode = versionCodeFromString(rootProject.version.toString())
            targetSdk = "targetSdk".versionInt()
            setProperty("archivesBaseName", "${rootProject.name}-v$versionName")
        }

        splits {
            abi {
                isEnable = true
                reset()
                include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
                isUniversalApk = true
            }
        }

        configureSigningIfAvailable(this)

        packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

        buildTypes.named("release") {
            isMinifyEnabled = RELEASE_IS_MINIFY_ENABLED
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        lint {
            disable.add("NullSafeMutableLiveData")
        }
    }
}

fun versionCodeFromString(version: String): Int {
    val (major, minor, patch) = version.split(".").map { it.toIntOrNull() ?: 0 }
    return major * 10000 + minor * 100 + patch
}

private fun Project.configureSigningIfAvailable(android: ApplicationExtension) {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) return

    val localProperties = Properties().apply {
        load(localPropertiesFile.inputStream())
    }

    val signingProps = listOf(
        "signing.store.file",
        "signing.store.password",
        "signing.key.alias",
        "signing.key.password"
    )

    val hasSigningConfig = signingProps.all { localProperties.getProperty(it) != null }

    if (hasSigningConfig) {
        android.signingConfigs {
            create("release") {
                storeFile = rootProject.file(localProperties.getProperty("signing.store.file"))
                storePassword = localProperties.getProperty("signing.store.password")
                keyAlias = localProperties.getProperty("signing.key.alias")
                keyPassword = localProperties.getProperty("signing.key.password")
            }
        }

        android.buildTypes.named("release") {
            signingConfig = android.signingConfigs.getByName("release")
        }
    }
}