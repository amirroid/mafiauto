package ir.amirroid.mafiauto.navigation.utils

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Intro : Screen

    @Serializable
    data object Lobby : Screen

    @Serializable
    data object AssignRoles : Screen
}