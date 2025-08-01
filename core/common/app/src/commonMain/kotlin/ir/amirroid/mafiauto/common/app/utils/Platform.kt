package ir.amirroid.mafiauto.common.app.utils

enum class Platform {
    ANDROID,
    IOS,
    DESKTOP
}

expect fun getCurrentPlatform(): Platform


val Platform.isDesktop: Boolean get() = this == Platform.DESKTOP
val Platform.isIos: Boolean get() = this == Platform.IOS
val Platform.isAndroid: Boolean get() = this == Platform.ANDROID