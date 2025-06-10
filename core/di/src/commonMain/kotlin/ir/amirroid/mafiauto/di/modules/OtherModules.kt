package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.data.memory.PlayerMemoryHolder
import ir.amirroid.mafiauto.data.memory.RoleMemoryHolder
import ir.amirroid.mafiauto.game.engine.provider.RolesProvider
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val otherModules = module {
    factoryOf(::RolesProvider)
    singleOf(::PlayerMemoryHolder)
    singleOf(::RoleMemoryHolder)
}