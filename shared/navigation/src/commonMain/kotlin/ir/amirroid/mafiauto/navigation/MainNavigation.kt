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
import ir.amirroid.mafiauto.guide.screen.GameGuideScreen
import ir.amirroid.mafiauto.intro.screen.IntroScreen
import ir.amirroid.mafiauto.intro.screen.LobbyScreen
import ir.amirroid.mafiauto.libraries.screen.LibrariesScreen
import ir.amirroid.mafiauto.logs.screen.GameLogsScreen
import ir.amirroid.mafiauto.navigation.utils.Screen
import ir.amirroid.mafiauto.navigation.utils.navigateIfNotCurrent
import ir.amirroid.mafiauto.night.screen.NightActionsScreen
import ir.amirroid.mafiauto.reveal.screen.RevealRolesScreen
import ir.amirroid.mafiauto.room.screen.GameRoomScreen
import ir.amirroid.mafiauto.settings.screen.SettingsScreen

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
                        onStartGame = { navController.navigateIfNotCurrent(Screen.Groups) },
                        onSettingsClick = { navController.navigateIfNotCurrent(Screen.Settings) },
                        onGuideClick = { navController.navigateIfNotCurrent(Screen.Guide) }
                    )
                }
                composable<Screen.Groups> {
                    GroupsScreen(
                        onBack = navController::navigateUp,
                        animatedContentScope = this,
                        onSelectGroup = { id ->
                            navController.navigateIfNotCurrent(Screen.Lobby(groupId = id))
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
                            onPickPlayers = { navController.navigateIfNotCurrent(Screen.AssignRoles) }
                        )
                    }
                }
                composable<Screen.AssignRoles> {
                    AssignRolesScreen(
                        onBack = navController::navigateUp,
                        onPickRoles = { navController.navigateIfNotCurrent(Screen.RevealRoles) }
                    )
                }
                composable<Screen.RevealRoles> {
                    RevealRolesScreen(
                        onBack = navController::navigateUp,
                        onStartGame = {
                            navController.navigateIfNotCurrent(Screen.GameRoom) {
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
                        onNight = { navController.navigateIfNotCurrent(Screen.NightActions) },
                        onFinalDebate = { navController.navigateIfNotCurrent(Screen.FinalDebate) },
                        onShowSummary = {
                            navController.navigateIfNotCurrent(Screen.GameLogs) {
                                popUpTo<Screen.GameRoom> {
                                    inclusive = true
                                }
                            }
                        }
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
                composable<Screen.Settings> {
                    SettingsScreen(
                        onBack = navController::navigateUp,
                        onOpenLibraries = { navController.navigateIfNotCurrent(Screen.Libraries) }
                    )
                }
                composable<Screen.Libraries> {
                    LibrariesScreen(
                        onBack = navController::navigateUp,
                    )
                }
                composable<Screen.GameLogs> {
                    GameLogsScreen(
                        onBack = navController::navigateUp,
                    )
                }
                composable<Screen.Guide> {
                    GameGuideScreen(
                        onBack = navController::navigateUp,
                    )
                }
            }
        }
    }
}