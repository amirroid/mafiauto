package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.db.SqlDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.database.dao.player.PlayerDao
import ir.amirroid.mafiauto.database.dao.player.PlayerDaoImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun Module.configureDriver()

val databaseModule = module {
    configureDriver()
    single {
        AppDatabase(get<SqlDriver>())
    }
    single { get<AppDatabase>().playerEntityQueries }
    singleOf(::PlayerDaoImpl).bind<PlayerDao>()
}