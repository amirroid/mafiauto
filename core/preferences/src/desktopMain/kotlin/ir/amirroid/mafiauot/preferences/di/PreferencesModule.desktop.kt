package ir.amirroid.mafiauot.preferences.di

import ir.amirroid.mafiauto.common.app.utils.AppInfo
import org.koin.core.module.Module
import java.io.File

actual fun Module.configurePreferences() {
    factory(qualifier = StorePathQualifier) {
        val baseCacheDir = File(System.getProperty("user.home"), ".${AppInfo.appName}/cache")
        baseCacheDir.mkdirs()
        baseCacheDir.absolutePath
    }
}