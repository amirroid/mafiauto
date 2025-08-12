package ir.amirroid.mafiauto.update_checker

import ir.amirroid.mafiauto.common.app.utils.AppFlavors
import ir.amirroid.mafiauto.common.app.utils.AppInfo
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val updaterModule = if (AppInfo.favor == AppFlavors.DEFAULT) defaultModule else module {
    factoryOf(::BazaarUpdateCheckerImpl).bind<UpdateChecker>()
}