package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.database.DB_NAME
import org.koin.core.module.Module

actual fun Module.configureDriver() {
    single<SqlDriver> {
        val dbPath = "$DB_NAME.db"
        val file = java.io.File(dbPath)

        val driver = JdbcSqliteDriver("jdbc:sqlite:$dbPath")

        if (!file.exists()) {
            AppDatabase.Schema.create(driver)
        }

        driver
    }
}