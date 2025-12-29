plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.local.android.application)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":composeApp"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.runtime)
}