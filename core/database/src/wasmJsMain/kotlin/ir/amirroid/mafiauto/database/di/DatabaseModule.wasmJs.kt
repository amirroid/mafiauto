package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import ir.amirroid.mafiauto.database.enableForeignKeys
import org.koin.core.module.Module
import org.w3c.dom.Worker


private val workerScriptUrl: String =
    js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")


actual fun Module.configureDriver() {
    single<SqlDriver> {
        WebWorkerDriver(Worker(workerScriptUrl)).apply {
            enableForeignKeys()
        }
    }
}