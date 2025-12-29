import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.android.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.navigation.compose)


                listOf(
                    ProjectPaths.intro,
                    ProjectPaths.groups,
                    ProjectPaths.lobby,
                    ProjectPaths.gameLogs,
                    ProjectPaths.libraries,
                    ProjectPaths.night,
                    ProjectPaths.settings,
                    ProjectPaths.assignRoles,
                    ProjectPaths.finalDebate,
                    ProjectPaths.room,
                    ProjectPaths.reveal,
                    ProjectPaths.guide,
                ).forEach {
                    implementation(project(it))
                }
            }
        }
    }
}