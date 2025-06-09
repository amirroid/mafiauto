package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.intro.viewmodel.LobbyViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LobbyViewModel)
}