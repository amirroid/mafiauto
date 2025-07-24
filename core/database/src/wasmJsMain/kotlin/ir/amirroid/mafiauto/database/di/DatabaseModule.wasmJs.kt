package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import ir.amirroid.mafiauto.AppDatabase
import org.koin.core.module.Module
import org.w3c.dom.Worker


actual fun Module.configureDriver() {
    single<SqlDriver> {
        val driver = WebWorkerDriver(
            Worker("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
        )
        runCatching { AppDatabase.Schema.create(driver) }
        driver
    }
}