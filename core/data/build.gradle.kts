import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(ProjectPaths.engine))
                implementation(project(ProjectPaths.database))
                implementation(project(ProjectPaths.compat))
                implementation(project(ProjectPaths.domain))
                implementation(project(ProjectPaths.preferences))
                implementation(project(ProjectPaths.network))
                implementation(project(ProjectPaths.updateChcker))
            }
        }
    }
}