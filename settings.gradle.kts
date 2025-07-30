rootProject.name = "Mafiauto"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
    versionCatalogs.create("androidLibs") {
        from(files("gradle/androidLibs.versions.toml"))
    }
}

include(":composeApp")
include(":core:theme")
include(":core:design-system")
include(":core:resources")
include(":core:di")
include(":core:data")
include(":core:domain")
include(":core:update-checker")
include(":core:database")
include(":core:preferences")
include(":core:ui-models")
include(":core:network")
include(":core:common:compose")
include(":core:common:app")


include(":shared:markdown")
include(":shared:navigation")
include(":shared:compat")

include(":game:engine")

include(":feature:intro")
include(":feature:settings")
include(":feature:groups")
include(":feature:lobby")
include(":feature:assign-roles")
include(":feature:reveal")
include(":feature:room")
include(":feature:libraries")
include(":feature:night")
include(":feature:guide")
include(":feature:game-logs")
include(":feature:final-debate")

includeBuild("build-logic")
