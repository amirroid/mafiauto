package ir.amirroid.mafiauto.navigation.utils

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Intro : Screen

    @Serializable
    data object Settings : Screen

    @Serializable
    data object Groups : Screen

    @Serializable
    data class Lobby(val groupId: Long) : Screen

    @Serializable
    data object AssignRoles : Screen

    @Serializable
    data object RevealRoles : Screen

    @Serializable
    data object GameRoom : Screen

    @Serializable
    data object NightActions : Screen

    @Serializable
    data object FinalDebate : Screen

    @Serializable
    data object Libraries : Screen

    @Serializable
    data object GameLogs : Screen

    @Serializable
    data object Guide : Screen
}