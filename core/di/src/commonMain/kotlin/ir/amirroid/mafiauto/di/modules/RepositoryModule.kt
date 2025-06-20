package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.data.repository.game.GameRepositoryImpl
import ir.amirroid.mafiauto.data.repository.player_role.PlayerRoleRepositoryImpl
import ir.amirroid.mafiauto.data.repository.role.RoleRepositoryImpl
import ir.amirroid.mafiauto.domain.repository.RoleRepository
import ir.amirroid.mafiauto.data.repository.player.PlayerRepositoryImpl
import ir.amirroid.mafiauto.domain.repository.GameRepository
import ir.amirroid.mafiauto.domain.repository.GroupsRepository
import ir.amirroid.mafiauto.data.repository.group.GroupsRepositoryImpl
import ir.amirroid.mafiauto.domain.repository.PlayerRoleRepository
import ir.amirroid.mafiauto.domain.repository.PlayerRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::RoleRepositoryImpl).bind<RoleRepository>()
    factoryOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    factoryOf(::PlayerRoleRepositoryImpl).bind<PlayerRoleRepository>()
    factoryOf(::GameRepositoryImpl).bind<GameRepository>()
    factoryOf(::GroupsRepositoryImpl).bind<GroupsRepository>()
}