import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.koin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(ProjectPaths.domain))
                implementation(project(ProjectPaths.engine))
                implementation(project(ProjectPaths.uiModels))
            }
        }
    }
}