import ir.amirroid.mafiauto.buildSrc.AppInfo

// For generate interfaces: generateCommonMainDatabaseInterface

plugins {
    alias(libs.plugins.local.kotlin)
    alias(libs.plugins.local.android.library)
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
    sourceSets.desktopMain.dependencies {
        implementation(libs.sqlite.driver)
    }
    sourceSets.wasmJsMain.dependencies {
        implementation("app.cash.sqldelight:web-worker-driver:2.1.0")
        implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.1.0"))
        implementation(npm("sql.js", "1.8.0"))
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set(AppInfo.namespace)

            schemaOutputDirectory.set(
                file("src/commonMain/sqldelight/${AppInfo.namespace.replace('.', '/')}")
            )
            verifyMigrations.set(true)
            generateAsync.set(true)
            deriveSchemaFromMigrations.set(true)
        }
    }
}