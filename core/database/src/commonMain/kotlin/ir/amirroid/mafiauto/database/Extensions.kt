package ir.amirroid.mafiauto.database

import app.cash.sqldelight.db.SqlDriver

fun SqlDriver.enableForeignKeys() {
    execute(null, "PRAGMA foreign_keys = ON;", 0)
}