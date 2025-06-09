package ir.amirroid.mafiauto.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.amirroid.mafiauto.common.compose.components.ScreenContent
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.intro.screen.IntroScreen
import ir.amirroid.mafiauto.navigation.utils.Screen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Intro, enterTransition = {
            slideIntoContainer(SlideDirection.Left)
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.Right)
        }

    ) {
        composable<Screen.Intro> {
            IntroScreen(
                onStartGame = {
                    navController.navigate(Screen.Lobby)
                }
            )
        }
        composable<Screen.Lobby> {

        }
    }
}