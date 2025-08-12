package ir.amirroid.mafiauto.common.app.utils

import ir.amirroid.mafiauto.BuildKonfig

enum class AppFlavors {
    DEFAULT, BAZAAR
}

@Suppress("ConstPropertyName")
object AppInfo {
    const val appName = "mafiauto"
    val version = BuildKonfig.APP_VERSION
    const val githubLink = "https://github.com/amirroid/mafiauto"
    val favor = AppFlavors.valueOf(BuildKonfig.FLAVOR.uppercase())
}