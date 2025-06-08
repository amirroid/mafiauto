package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.RoleAction

sealed interface Role {
    val englishName: String
    val persianName: String
    val alignment: Alignment
    val key: String

    fun getNightAction(): RoleAction?
}

enum class Alignment { Mafia, Civilian, Neutral }