import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.kotlin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(ProjectPaths.network))

            implementation(libs.ktor.client.core)
        }
    }
}