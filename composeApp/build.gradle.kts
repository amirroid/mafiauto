plugins {
    alias(libs.plugins.local.android.application)
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.koin)
}


kotlin.sourceSets.commonMain {
    dependencies {
        implementation(project(":core:navigation"))
    }
}