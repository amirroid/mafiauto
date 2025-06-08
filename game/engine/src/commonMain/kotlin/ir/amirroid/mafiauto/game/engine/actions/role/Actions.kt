package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.state.GameState

data class KillAction(
    override val actorKey: String,
    val targetPlayerId: Int,
    override val delayInDays: Int = 0
) : RoleAction {
    override fun apply(gameState: GameState) {
//        gameState.killPlayer(targetPlayerId)
    }
}

data class SaveAction(
    override val actorKey: String,
    val targetPlayerId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
//        gameState.savePlayer(targetPlayerId)
    }
}

data class InvestigateAction(
    override val actorKey: String,
    val targetPlayerId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
//        val alignment = gameState.getPlayerAlignment(targetPlayerId)
    }
}

data class SilentAction(
    override val actorKey: String,
    val targetPlayerId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
//        gameState.silencePlayer(targetPlayerId)
    }
}

data class ConvertAction(
    override val actorKey: String,
    val targetPlayerId: Int,
    val newRoleKey: String
) : RoleAction {
    override fun apply(gameState: GameState) {
//        gameState.convertPlayer(targetPlayerId, newRoleKey)
    }
}

data class RevealRoleAction(
    override val actorKey: String,
    val targetPlayerId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
//        val role = gameState.getPlayerRole(targetPlayerId)
    }
}