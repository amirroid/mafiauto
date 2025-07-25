package ir.amirroid.mafiauto.database.di

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.database.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

actual fun Module.configureDriver() {
    single<SqlDriver> {
        val synchronousScheme = AppDatabase.Schema.synchronous()
        AndroidSqliteDriver(
            schema = synchronousScheme,
            context = androidContext(),
            name = DB_NAME,
            callback = object : AndroidSqliteDriver.Callback(synchronousScheme) {
                override fun onConfigure(db: SupportSQLiteDatabase) {
                    super.onConfigure(db)
                    db.execSQL("PRAGMA foreign_keys=ON;")
                }
            }
        )
    }
}