package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.database.DB_NAME
import org.koin.core.module.Module

actual fun Module.configureDriver() {
    single { NativeSqliteDriver(schema = AppDatabase.Schema, name = DB_NAME) }
}