package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.database.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

actual fun Module.configureDriver() {
    single {
        AndroidSqliteDriver(schema = AppDatabase.Schema, context = androidContext(), name = DB_NAME)
    }
}