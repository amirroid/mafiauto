package ir.amirroid.mafiauto.update_checker

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val updaterModule = module {
    factoryOf(::BazaarUpdateCheckerImpl).bind<UpdateChecker>()
}