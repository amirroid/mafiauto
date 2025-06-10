import ir.amirroid.mafiauto.buildSrc.AppInfo

// For generate interfaces: generateCommonMainDatabaseInterface

plugins {
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.local.kotlin)
    alias(libs.plugins.local.koin)
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets.androidMain.dependencies {
        implementation(libs.sqldelight.driver.android)
    }
    sourceSets.commonMain.dependencies {
        implementation(libs.sqldelight.runtime)
        implementation(libs.sqldelight.coroutines)
    }
    sourceSets.nativeMain.dependencies {
        implementation(libs.sqldelight.driver.native)
    }

    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set(AppInfo.namespace)
        }
    }
}