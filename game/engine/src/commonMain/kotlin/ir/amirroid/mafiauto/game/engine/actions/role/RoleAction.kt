package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.state.GameState

interface RoleAction {
    val actorKey: String
    val targetId: Int
    val delayInDays: Int get() = 0
    fun apply(gameState: GameState)
}