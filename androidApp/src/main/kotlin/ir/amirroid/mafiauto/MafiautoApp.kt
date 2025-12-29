package ir.amirroid.mafiauto

import android.app.Application

class MafiautoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        configureDependencyInjection(this)
    }
}