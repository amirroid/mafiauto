package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.game.engine.provider.RolesProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val otherModules = module {
    singleOf(::RolesProvider)
}