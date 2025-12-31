import ir.amirroid.mafiauto.buildSrc.ProjectPaths
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.koin)
}

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(project(ProjectPaths.navigation))
        implementation(project(ProjectPaths.di))
        implementation(project(ProjectPaths.domain))
        implementation(project(ProjectPaths.uiModels))
    }
}


compose.desktop {
    application {
        mainClass = "ir.amirroid.mafiauto.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ir.amirroid.mafiauto"
            packageVersion = rootProject.version.toString()
        }
    }
}