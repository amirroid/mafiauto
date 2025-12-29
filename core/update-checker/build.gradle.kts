import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.kotlin)
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.koin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(ProjectPaths.network))
        }
        androidMain.dependencies {
            implementation(libs.bazaar.updater)
        }
    }
}