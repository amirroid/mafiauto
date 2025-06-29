package ir.amirroid.mafiauto

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val projectModule = module {
    viewModelOf(::AppViewModel)
}