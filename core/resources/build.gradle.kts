plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.compose.multiplatform)
}

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(compose.dependencies.components.resources)
    }
}