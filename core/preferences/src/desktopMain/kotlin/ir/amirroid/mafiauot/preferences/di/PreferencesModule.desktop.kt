package ir.amirroid.mafiauot.preferences.di

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_FILENAME
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_VERSION
import ir.amirroid.mafiauto.common.app.utils.AppInfo
import kotlinx.io.files.Path
import org.koin.core.module.Module
import java.io.File

actual fun Module.configurePreferences() {
    factory(qualifier = StorePathQualifier) {
        val baseCacheDir = File(System.getProperty("user.home"), ".${AppInfo.appName}/cache")
        baseCacheDir.mkdirs()
        baseCacheDir.absolutePath
    }
    single<KStore<SettingsConfig>> {
        storeOf(
            file = Path(get<String>(StorePathQualifier).plus("/$SETTINGS_FILENAME")),
            version = SETTINGS_VERSION,
            json = get()
        )
    }
}