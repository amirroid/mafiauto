plugins {
    alias(libs.plugins.local.compose.multiplatform)
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.koin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kstore)
        }
        androidMain.dependencies {
            implementation(libs.kstore.file)
        }
        iosMain.dependencies {
            implementation(libs.kstore.file)
        }
        desktopMain.dependencies {
            implementation(libs.kstore.file)
        }
        wasmJsMain.dependencies {
            implementation(libs.kstore.storage)
        }
    }
}