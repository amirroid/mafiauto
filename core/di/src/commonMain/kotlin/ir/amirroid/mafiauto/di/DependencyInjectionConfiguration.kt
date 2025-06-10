package ir.amirroid.mafiauto.di

import ir.amirroid.mafiauto.database.di.databaseModule
import ir.amirroid.mafiauto.di.modules.dispatcherModule
import ir.amirroid.mafiauto.di.modules.otherModules
import ir.amirroid.mafiauto.di.modules.repositoryModule
import ir.amirroid.mafiauto.di.modules.useCaseModule
import ir.amirroid.mafiauto.di.modules.viewModelModule
import ir.amirroid.mafiauto.game.engine.di.gameEngineModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object DependencyInjectionConfiguration {
    fun configure(config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(
                otherModules,
                viewModelModule,
                useCaseModule,
                repositoryModule,
                dispatcherModule,
                databaseModule,
                gameEngineModule
            )
        }
    }
}