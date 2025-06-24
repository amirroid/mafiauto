package ir.amirroid.mafiauto.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ir.amirroid.mafiauto.assign_roles.screen.AssignRolesScreen
import ir.amirroid.mafiauto.common.compose.locales.LocalSharedTransitionScope
import ir.amirroid.mafiauto.final_debate.screen.FinalDebateScreen
import ir.amirroid.mafiauto.groups.screen.GroupsScreen
import ir.amirroid.mafiauto.intro.screen.IntroScreen
import ir.amirroid.mafiauto.intro.screen.LobbyScreen
import ir.amirroid.mafiauto.navigation.utils.Screen
import ir.amirroid.mafiauto.night.screen.NightActionsScreen
import ir.amirroid.mafiauto.reveal.screen.RevealRolesScreen
import ir.amirroid.mafiauto.room.screen.GameRoomScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Intro,
                enterTransition = {
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
                        onStartGame = { navController.navigate(Screen.Groups) },
                    )
                }
                composable<Screen.Groups> {
                    GroupsScreen(
                        onBack = navController::navigateUp,
                        animatedContentScope = this,
                        onSelectGroup = {
                            navController.navigate(Screen.Lobby(groupId = it))
                        }
                    )
                }
                composable<Screen.Lobby> {
                    Box(
                        modifier = Modifier.sharedBounds(
                            rememberSharedContentState(it.toRoute<Screen.Lobby>().groupId),
                            this,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
                        )
                    ) {
                        LobbyScreen(
                            onBack = navController::navigateUp,
                            onPickPlayers = { navController.navigate(Screen.AssignRoles) }
                        )
                    }
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
                        onStartGame = {
                            navController.navigate(Screen.GameRoom) {
                                popUpTo(Screen.Groups::class) {
                                    inclusive = false
                                }
                            }
                        }
                    )
                }
                composable<Screen.GameRoom> {
                    GameRoomScreen(
                        onBack = navController::navigateUp,
                        onNight = { navController.navigate(Screen.NightActions) },
                        onFinalDebate = { navController.navigate(Screen.FinalDebate) }
                    )
                }
                composable<Screen.NightActions> {
                    NightActionsScreen(
                        onBack = navController::navigateUp
                    )
                }
                composable<Screen.FinalDebate> {
                    FinalDebateScreen(
                        onBack = navController::navigateUp
                    )
                }
            }
        }
    }
}