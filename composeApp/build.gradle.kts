import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.application)
    alias(libs.plugins.local.compose.multiplatform)
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