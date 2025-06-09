import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.kotlin)
    alias(libs.plugins.local.koin)
}

kotlin.sourceSets.commonMain {
    dependencies {
        listOf(
            ProjectPaths.lobby,
            ProjectPaths.engine,
            ProjectPaths.domain,
            ProjectPaths.data,
        ).forEach {
            implementation(project(it))
        }
    }
}