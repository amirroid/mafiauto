import ir.amirroid.mafiauto.buildSrc.ProjectPaths

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
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
                    ProjectPaths.night,
                    ProjectPaths.assignRoles,
                    ProjectPaths.finalDebate,
                    ProjectPaths.room,
                    ProjectPaths.reveal,
                ).forEach {
                    implementation(project(it))
                }
            }
        }
    }
}