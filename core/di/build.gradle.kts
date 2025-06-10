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
            ProjectPaths.assignRoles,
            ProjectPaths.engine,
            ProjectPaths.domain,
            ProjectPaths.data,
            ProjectPaths.database,
        ).forEach {
            implementation(project(it))
        }
    }
}