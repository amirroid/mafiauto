package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.data.memory.PlayerMemoryHolder
import ir.amirroid.mafiauto.data.memory.PlayersWithRoleMemoryHelper
import ir.amirroid.mafiauto.data.memory.RoleMemoryHolder
import ir.amirroid.mafiauto.game.engine.provider.last_card.*
import ir.amirroid.mafiauto.game.engine.provider.roles.*
import ir.amirroid.mafiauto.update_checker.UpdateChecker
import ir.amirroid.mafiauto.update_checker.UpdateCheckerImpl
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val otherModules = module {
    factoryOf(::RolesProviderImpl).bind<RolesProvider>()
    factoryOf(::LastCardsProviderImpl).bind<LastCardsProvider>()
    singleOf(::PlayerMemoryHolder)
    singleOf(::RoleMemoryHolder)
    singleOf(::PlayersWithRoleMemoryHelper)
    factoryOf(::UpdateCheckerImpl).bind<UpdateChecker>()
}