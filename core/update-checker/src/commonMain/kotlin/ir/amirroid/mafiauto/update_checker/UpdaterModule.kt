package ir.amirroid.mafiauto.update_checker

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val updaterModule: Module

internal val defaultModule: Module
    get() = module {
        factoryOf(::UpdateCheckerImpl).bind<UpdateChecker>()
    }