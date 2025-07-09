package ir.amirroid.mafiauto.di

import ir.amirroid.mafiauot.preferences.di.preferencesModule
import ir.amirroid.mafiauto.database.di.databaseModule
import ir.amirroid.mafiauto.di.modules.dispatcherModule
import ir.amirroid.mafiauto.di.modules.jsonModule
import ir.amirroid.mafiauto.di.modules.otherModules
import ir.amirroid.mafiauto.di.modules.repositoryModule
import ir.amirroid.mafiauto.di.modules.useCaseModule
import ir.amirroid.mafiauto.di.modules.viewModelModule
import ir.amirroid.mafiauto.game.engine.di.gameEngineModule
import ir.amirroid.mafiauto.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object DependencyInjectionConfiguration {
    fun configure(config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(
                otherModules,
                networkModule,
                jsonModule,
                viewModelModule,
                useCaseModule,
                repositoryModule,
                dispatcherModule,
                databaseModule,
                gameEngineModule,
                preferencesModule,
            )
        }
    }
}