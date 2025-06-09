package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.RoleAction
import org.jetbrains.compose.resources.StringResource

sealed interface Role {
    val name: StringResource
    val alignment: Alignment
    val key: String

    fun getNightAction(): RoleAction?
}

enum class Alignment { Mafia, Civilian, Neutral }