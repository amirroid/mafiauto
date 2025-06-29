package ir.amirroid.mafiauot.preferences.di

import kotlinx.cinterop.ExperimentalForeignApi
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
}