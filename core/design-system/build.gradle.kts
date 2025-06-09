plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
}

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(libs.haze)
        implementation(libs.haze.materials)
    }
}