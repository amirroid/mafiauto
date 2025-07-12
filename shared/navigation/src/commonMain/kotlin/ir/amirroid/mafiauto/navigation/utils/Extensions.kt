package ir.amirroid.mafiauto.navigation.utils

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun <T : Any> NavHostController.navigateIfNotCurrent(
    destination: T,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    val current = currentBackStackEntry?.destination
    if (current?.hasRoute(destination::class) != true) {
        navigate(destination, builder)
    }
}