plugins {
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.koin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.aboutLibraries.compose.core)
            }
        }
    }
}