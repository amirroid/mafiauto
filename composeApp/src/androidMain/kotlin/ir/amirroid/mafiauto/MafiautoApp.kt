package ir.amirroid.mafiauto

import android.app.Application
import ir.amirroid.mafiauto.di.DependencyInjectionConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MafiautoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DependencyInjectionConfiguration.configure {
            androidContext(applicationContext)
            modules(projectModule)
        }
    }
}