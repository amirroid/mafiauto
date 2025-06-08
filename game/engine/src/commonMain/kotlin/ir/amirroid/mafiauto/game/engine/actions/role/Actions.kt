package ir.amirroid.mafiauto.game.engine.actions.role

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.state.GameState

@Immutable
data class KillAction(
    override val actorKey: String,
    override val targetId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
        gameState.kill(targetId)
    }
}

@Immutable
data class SaveAction(
    override val actorKey: String, override val targetId: Int,
) : RoleAction {
    override fun apply(gameState: GameState) {
        gameState.save(targetId)
    }
}

@Immutable
data class InvestigateAction(
    override val actorKey: String,
    override val targetId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
//        val alignment = gameState.getPlayerAlignment(targetPlayerId)
    }
}

@Immutable
data class SilentAction(
    override val actorKey: String, override val targetId: Int,
) : RoleAction {
    override fun apply(gameState: GameState) {
//        gameState.silencePlayer(targetPlayerId)
    }
}

@Immutable
data class ConvertAction(
    override val actorKey: String,
    override val targetId: Int,
    val newRoleKey: String
) : RoleAction {
    override fun apply(gameState: GameState) {
//        gameState.convertPlayer(targetPlayerId, newRoleKey)
    }
}

@Immutable
data class RevealRoleAction(
    override val actorKey: String,
    override val targetId: Int
) : RoleAction {
    override fun apply(gameState: GameState) {
//        val role = gameState.getPlayerRole(targetPlayerId)
    }
}