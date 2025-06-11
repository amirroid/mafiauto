package ir.amirroid.mafiauto.game.engine.actions.role

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.GameEngine

@Immutable
data class KillAction(
    override val actorKey: String,
) : RoleAction {
    override fun apply(gameState: GameEngine) {
//        gameState.kill(targetId)
    }
}

@Immutable
data class SaveAction(
    override val actorKey: String
) : RoleAction {
    override fun apply(gameState: GameEngine) {
//        gameState.save(targetId)
    }
}

@Immutable
data class InvestigateAction(
    override val actorKey: String,
) : RoleAction {
    override fun apply(gameState: GameEngine) {
//        val alignment = gameState.getPlayerAlignment(targetPlayerId)
    }
}

@Immutable
data class SilentAction(
    override val actorKey: String
) : RoleAction {
    override fun apply(gameState: GameEngine) {
//        gameState.silencePlayer(targetPlayerId)
    }
}

@Immutable
data class ConvertAction(
    override val actorKey: String,
    val newRoleKey: String
) : RoleAction {
    override fun apply(gameState: GameEngine) {
//        gameState.convertPlayer(targetPlayerId, newRoleKey)
    }
}

@Immutable
data class RevealRoleAction(
    override val actorKey: String,
) : RoleAction {
    override fun apply(gameState: GameEngine) {
//        val role = gameState.getPlayerRole(targetPlayerId)
    }
}