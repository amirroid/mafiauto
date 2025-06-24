package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.domain.usecase.game.*
import ir.amirroid.mafiauto.domain.usecase.player_role.*
import ir.amirroid.mafiauto.domain.usecase.role.*
import ir.amirroid.mafiauto.domain.usecase.player.*
import ir.amirroid.mafiauto.domain.usecase.groups.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetSingleRoleDescriptorUseCase)
    factoryOf(::GetAllRoleDescriptorsUseCase)
    factoryOf(::GetAllPlayersUseCase)
    factoryOf(::RemovePlayerUseCase)
    factoryOf(::AddPlayerUseCase)
    factoryOf(::SelectNewPlayersUseCase)
    factoryOf(::GetSelectedPlayersUseCase)
    factoryOf(::SelectNewRolesUseCase)
    factoryOf(::GetPlayerWithRolesAndSaveUseCase)
    factoryOf(::GetAllPlayersWithRolesUseCase)
    factoryOf(::OnStatusCheckedUseCase)
    factoryOf(::UndoStatusCheckUseCase)
    factoryOf(::GetStatusCheckCountUseCase)
    factoryOf(::GetAllInRoomPlayersUseCase)
    factoryOf(::StartGameUseCase)
    factoryOf(::KickPlayerUseCase)
    factoryOf(::UnKickPlayerUseCase)
    factoryOf(::GetCurrentPhaseUseCase)
    factoryOf(::GoToNextPhaseUseCase)
    factoryOf(::StartDefendingUseCase)
    factoryOf(::GetDefenseCandidatesUseCase)
    factoryOf(::HandleDefenseVoteResultUseCase)
    factoryOf(::GetAllLastCardsUseCase)
    factoryOf(::ApplyLastCardUseCase)
    factoryOf(::GetCurrentDayUseCase)
    factoryOf(::HandleNightActionsUseCase)
    factoryOf(::HandleFatePhaseUseCase)
    factoryOf(::GetMessagesUseCase)
    factoryOf(::GetAllGroupsUseCase)
    factoryOf(::AddNewGroupUseCase)
    factoryOf(::GetCurrentPlayerTurnIndexUseCase)
    factoryOf(::HandleFinalTrustVotesUseCase)
}