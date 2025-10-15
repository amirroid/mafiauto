package ir.amirroid.mafiauot.preferences.di

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_FILENAME
import ir.amirroid.mafiauot.preferences.utils.SETTINGS_VERSION
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.io.files.Path
import org.koin.core.module.Module
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private fun getCachePath(): String {
    val fileManager = NSFileManager.defaultManager
    val cachesUrl = fileManager.URLForDirectory(
        directory = NSCachesDirectory,
        appropriateForURL = null,
        create = false,
        inDomain = NSUserDomainMask,
        error = null
    )
    return cachesUrl?.path ?: throw IllegalStateException("NSCachesDirectory does not exists")
}

actual fun Module.configurePreferences() {
    factory(qualifier = StorePathQualifier) { getCachePath() }
    single<KStore<SettingsConfig>> {
        storeOf(
            file = Path(get<String>(StorePathQualifier).plus("/$SETTINGS_FILENAME")),
            version = SETTINGS_VERSION,
            json = get()
        )
    }
}