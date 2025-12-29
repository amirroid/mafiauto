plugins {
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.android.library)
}

kotlin.sourceSets.commonMain {
    dependencies {
        api(libs.haze)
    }
}