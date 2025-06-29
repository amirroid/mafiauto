package ir.amirroid.mafiauot.preferences.di

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauot.preferences.source.SettingsConfigPreferencesSource
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_FILENAME
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_VERSION
import ir.amirroid.mafiauot.preferences.utils.toPath
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val StorePathQualifier = qualifier("store_path")

expect fun Module.configurePreferences()

val preferencesModule = module {
    configurePreferences()
    single<KStore<SettingsConfig>> {
        storeOf(
            file = get<String>(StorePathQualifier).plus("/$SETTINGS_FILENAME").toPath(),
            version = SETTINGS_VERSION,
            json = get()
        )
    }
    factoryOf(::SettingsConfigPreferencesSource)
}
