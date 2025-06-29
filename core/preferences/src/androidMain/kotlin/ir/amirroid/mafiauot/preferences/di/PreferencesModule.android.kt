package ir.amirroid.mafiauot.preferences.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

actual fun Module.configurePreferences() {
    factory(qualifier = StorePathQualifier) { androidContext().cacheDir.path }
}