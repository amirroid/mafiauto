package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.database.DB_NAME
import ir.amirroid.mafiauto.database.enableForeignKeys
import org.koin.core.module.Module

actual fun Module.configureDriver() {
    single<SqlDriver> {
        val dbPath = "$DB_NAME.db"

        JdbcSqliteDriver(
            "jdbc:sqlite:$dbPath",
            schema = AppDatabase.Schema.synchronous()
        ).apply {
            enableForeignKeys()
        }
    }
}