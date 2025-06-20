package ir.amirroid.mafiauto.database.di

import app.cash.sqldelight.db.SqlDriver
import ir.amirroid.mafiauto.AppDatabase
import ir.amirroid.mafiauto.GroupEntityQueries
import ir.amirroid.mafiauto.PlayerEntityQueries
import ir.amirroid.mafiauto.database.dao.group.GroupDao
import ir.amirroid.mafiauto.database.dao.group.GroupDaoImpl
import ir.amirroid.mafiauto.database.dao.player.PlayerDao
import ir.amirroid.mafiauto.database.dao.player.PlayerDaoImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun Module.configureDriver()

val databaseModule = module {
    configureDriver()
    single<AppDatabase> {
        AppDatabase(get<SqlDriver>())
    }
    single<PlayerEntityQueries> { get<AppDatabase>().playerEntityQueries }
    single<GroupEntityQueries> { get<AppDatabase>().groupEntityQueries }
    singleOf(::PlayerDaoImpl).bind<PlayerDao>()
    singleOf(::GroupDaoImpl).bind<GroupDao>()
}