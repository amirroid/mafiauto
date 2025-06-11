package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.GameEngine

interface RoleAction {
    val actorKey: String
    val delayInDays: Int get() = 0
    fun apply(gameState: GameEngine)
}