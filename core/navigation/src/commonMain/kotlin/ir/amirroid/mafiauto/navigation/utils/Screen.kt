package ir.amirroid.mafiauto.navigation.utils

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Intro : Screen
}