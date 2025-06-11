import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(ProjectPaths.domain))
            }
        }
    }
}