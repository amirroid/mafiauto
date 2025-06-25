package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.base.ActionsHandler
import ir.amirroid.mafiauto.game.engine.models.InstantActionType
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources

data object Joker : Role {
    override val key = RoleKeys.JOKER
    override val name = Resources.strings.joker
    override val explanation = Resources.strings.jokerExplanation
    override val alignment = Alignment.Neutral
    override fun hasNightAction(players: List<Player>): Boolean = false
    override fun getNightAction() = null
    override val winsIfFinalDebate: Boolean = true

    override fun onEliminatedByVotes(players: List<Player>, handler: ActionsHandler) {
        handler.updatePhase(Phase.End(winnerAlignment = Alignment.Neutral))
    }
}

data object Nostradamus : Role {
    override val key = RoleKeys.NOSTRADAMUS
    override val name = Resources.strings.nostradamus
    override val explanation = Resources.strings.nostradamusExplanation
    override val alignment = Alignment.Neutral
    override val targetNightToWakingUp: Int = 0
    override val nightActionRequiredPicks: Int = 3
    override val instantActionType: InstantActionType = InstantActionType.SHOW_ALIGNMENTS_COUNT

    override fun getNightAction() = null
}