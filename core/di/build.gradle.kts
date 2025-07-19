import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.kotlin)
    alias(libs.plugins.local.koin)
}

kotlin.sourceSets.commonMain {
    dependencies {
        listOf(
            ProjectPaths.groups,
            ProjectPaths.lobby,
            ProjectPaths.assignRoles,
            ProjectPaths.reveal,
            ProjectPaths.settings,
            ProjectPaths.gameLogs,
            ProjectPaths.room,
            ProjectPaths.night,
            ProjectPaths.finalDebate,
            ProjectPaths.engine,
            ProjectPaths.domain,
            ProjectPaths.data,
            ProjectPaths.compat,
            ProjectPaths.database,
            ProjectPaths.network,
            ProjectPaths.updateChcker,
            ProjectPaths.preferences,
        ).forEach {
            implementation(project(it))
        }
    }
}