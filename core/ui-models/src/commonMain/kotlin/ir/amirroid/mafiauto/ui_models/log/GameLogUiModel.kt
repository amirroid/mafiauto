package ir.amirroid.mafiauto.ui_models.log

import ir.amirroid.mafiauto.ui_models.last_card.LastCardUiModel
import ir.amirroid.mafiauto.ui_models.night_action.NightActionUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

sealed class GameLogUiModel(val day: Int) {
    data class NightActions(
        val actions: List<NightActionUiModel>,
        val relatedDay: Int
    ) : GameLogUiModel(relatedDay)

    data class DefenseVotes(
        val playerVotes: ImmutableMap<PlayerWithRoleUiModel, Int>,
        val relatedDay: Int
    ) : GameLogUiModel(relatedDay)

    data class ElectedInDefense(
        val player: PlayerWithRoleUiModel,
        val relatedDay: Int
    ) : GameLogUiModel(relatedDay)

    data class ApplyLastCard(
        val player: PlayerWithRoleUiModel,
        val card: LastCardUiModel,
        val selectedPlayers: ImmutableList<PlayerWithRoleUiModel>,
        val relatedDay: Int
    ) : GameLogUiModel(relatedDay)

    data class Kick(
        val player: PlayerWithRoleUiModel,
        val isKicked: Boolean,
        val relatedDay: Int
    ) : GameLogUiModel(relatedDay)

    data class FinalDebate(
        val playersTrustVotes: Map<PlayerWithRoleUiModel, PlayerWithRoleUiModel>,
        val relatedDay: Int
    ) : GameLogUiModel(relatedDay)
}
