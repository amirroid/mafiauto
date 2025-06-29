package ir.amirroid.mafiauot.preferences.source

import io.github.xxfast.kstore.KStore
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauot.preferences.source.base.StorePreferencesSourceImpl

class SettingsConfigPreferencesSource(
    store: KStore<SettingsConfig>
) : StorePreferencesSourceImpl<SettingsConfig>(store)