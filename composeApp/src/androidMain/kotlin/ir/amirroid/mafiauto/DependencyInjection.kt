package ir.amirroid.mafiauto

import android.app.Application
import ir.amirroid.mafiauto.di.DependencyInjectionConfiguration
import org.koin.android.ext.koin.androidContext

fun configureDependencyInjection(application: Application) {
    DependencyInjectionConfiguration.configure {
        androidContext(application)
        modules(projectModule)
    }
}