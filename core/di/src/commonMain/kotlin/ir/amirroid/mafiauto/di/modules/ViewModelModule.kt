package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.assign_roles.viewmodel.AssignRolesViewModel
import ir.amirroid.mafiauto.intro.viewmodel.LobbyViewModel
import ir.amirroid.mafiauto.night.viewmodel.NightActionsViewModel
import ir.amirroid.mafiauto.reveal.viewmodel.RevealRolesViewModel
import ir.amirroid.mafiauto.room.viewmodel.GameRoomViewModel
import ir.amirroid.mafiauto.groups.viewmodel.GroupsViewModel
import ir.amirroid.mafiauto.final_debate.viewmodel.FinalDebateViewModel
import ir.amirroid.mafiauto.settings.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LobbyViewModel)
    viewModelOf(::AssignRolesViewModel)
    viewModelOf(::RevealRolesViewModel)
    viewModelOf(::GameRoomViewModel)
    viewModelOf(::NightActionsViewModel)
    viewModelOf(::GroupsViewModel)
    viewModelOf(::FinalDebateViewModel)
    viewModelOf(::SettingsViewModel)
}