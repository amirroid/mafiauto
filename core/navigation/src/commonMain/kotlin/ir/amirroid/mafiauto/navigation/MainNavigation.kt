package ir.amirroid.mafiauto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.amirroid.mafiauto.navigation.utils.Screen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Intro
    ) {
        composable<Screen.Intro> { }
    }
}