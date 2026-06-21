package ir.amirroid.mafiauot.preferences.di

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_FILENAME
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_VERSION
import kotlinx.io.files.Path
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

actual fun Module.configurePreferences() {
    factory(qualifier = StorePathQualifier) { androidContext().cacheDir.path }
    single<KStore<SettingsConfig>> {
        storeOf(
            file = Path(get<String>(StorePathQualifier).plus("/$SETTINGS_FILENAME")),
            version = SETTINGS_VERSION,
            json = get()
        )
    }
}