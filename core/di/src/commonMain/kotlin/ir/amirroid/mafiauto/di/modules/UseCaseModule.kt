package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.domain.usecase.game.GetPlayerWithRolesAndSaveUseCase
import ir.amirroid.mafiauto.domain.usecase.role.*
import ir.amirroid.mafiauto.domain.usecase.player.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetAllRoleDescriptorsUseCase)
    factoryOf(::GetAllPlayersUseCase)
    factoryOf(::RemovePlayerUseCase)
    factoryOf(::AddPlayerUseCase)
    factoryOf(::SelectNewPlayersUseCase)
    factoryOf(::GetSelectedPlayersUseCase)
    factoryOf(::SelectNewRolesUseCase)
    factoryOf(::GetPlayerWithRolesAndSaveUseCase)
}