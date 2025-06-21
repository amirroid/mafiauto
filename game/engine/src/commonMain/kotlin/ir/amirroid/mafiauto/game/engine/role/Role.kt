package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.RoleAction
import ir.amirroid.mafiauto.game.engine.models.InstantActionType
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.models.StringResourcesMessage
import ir.amirroid.mafiauto.game.engine.utils.NightActionOrder
import ir.amirroid.mafiauto.game.engine.utils.SubmitNightActionOrder
import org.jetbrains.compose.resources.StringResource

sealed interface Role {
    val name: StringResource
    val explanation: StringResource
    val alignment: Alignment
    val hasNightAction: Boolean
    val executionOrder: Int get() = NightActionOrder[key] ?: 100
    val submitExecutionOrder: Int get() = SubmitNightActionOrder[key] ?: 100
    val healthPoints: Int get() = 1
    val key: String
    val maxAbilityUses: Int get() = Int.MAX_VALUE
    val isOptionalAbility: Boolean get() = false
    val instantActionType: InstantActionType? get() = null
    val targetNightToWakingUp: Int? get() = null

    fun getNightAction(): RoleAction?
    fun getNightActionTargetPlayers(
        previewsTarget: Player?, allPlayers: List<Player>
    ): List<Player> = allPlayers.filter { it.isInGame && it.role.key != key }

    fun getNightActionMessage(players: List<Player>): StringResourcesMessage? = null
}

enum class Alignment { Mafia, Civilian, Neutral }