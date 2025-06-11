package ir.amirroid.mafiauto.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.amirroid.mafiauto.assign_roles.screen.AssignRolesScreen
import ir.amirroid.mafiauto.intro.screen.IntroScreen
import ir.amirroid.mafiauto.intro.screen.LobbyScreen
import ir.amirroid.mafiauto.navigation.utils.Screen
import ir.amirroid.mafiauto.reveal.screen.RevealRolesScreen
import ir.amirroid.mafiauto.room.screen.GameRoomScreen

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
            LobbyScreen(
                onBack = navController::navigateUp,
                onPickPlayers = { navController.navigate(Screen.AssignRoles) }
            )
        }
        composable<Screen.AssignRoles> {
            AssignRolesScreen(
                onBack = navController::navigateUp,
                onPickRoles = { navController.navigate(Screen.RevealRoles) }
            )
        }
        composable<Screen.RevealRoles> {
            RevealRolesScreen(
                onBack = navController::navigateUp,
                onStartGame = { navController.navigate(Screen.GameRoom) }
            )
        }
        composable<Screen.GameRoom> {
            GameRoomScreen(
                onBack = navController::navigateUp
            )
        }
    }
}