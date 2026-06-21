package ir.amirroid.mafiauot.preferences.di

import ir.amirroid.mafiauot.preferences.source.SettingsConfigPreferencesSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val StorePathQualifier = qualifier("store_path")

expect fun Module.configurePreferences()

val preferencesModule = module {
    configurePreferences()
    factoryOf(::SettingsConfigPreferencesSource)
}
