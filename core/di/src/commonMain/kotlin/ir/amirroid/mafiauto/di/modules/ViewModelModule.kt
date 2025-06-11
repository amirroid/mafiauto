package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.assign_roles.viewmodel.AssignRolesViewModel
import ir.amirroid.mafiauto.intro.viewmodel.LobbyViewModel
import ir.amirroid.mafiauto.reveal.viewmodel.RevealRolesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LobbyViewModel)
    viewModelOf(::AssignRolesViewModel)
    viewModelOf(::RevealRolesViewModel)
}