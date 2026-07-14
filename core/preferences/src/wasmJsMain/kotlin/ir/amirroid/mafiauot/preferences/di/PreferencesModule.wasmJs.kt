package ir.amirroid.mafiauot.preferences.di

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import org.koin.core.module.Module

actual fun Module.configurePreferences() {
    single<KStore<SettingsConfig>> { storeOf(key = "settings") }
}