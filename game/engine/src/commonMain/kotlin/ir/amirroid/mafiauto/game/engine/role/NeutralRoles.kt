package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources

data object Joker : Role {
    override val key = RoleKeys.JOKER
    override val name = Resources.strings.joker
    override val explanation = Resources.strings.jokerExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = false
    override fun getNightAction() = null
}

data object CultLeader : Role {
    override val key = RoleKeys.CULT_LEADER
    override val name = Resources.strings.cultLeader
    override val explanation = Resources.strings.cultLeaderExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = true
    override fun getNightAction() = null
    override fun getNightActionTargetPlayers(
        previewsTarget: Player?,
        allPlayers: List<Player>
    ): List<Player> {
        return allPlayers.filter { player ->
            player.role !is CultLeader
        }
    }
}


data object Bomber : Role {
    override val key = RoleKeys.BOMBER
    override val name = Resources.strings.bomber
    override val explanation = Resources.strings.bomberExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = true
    override fun getNightAction() = null
}


data object Nostradamus : Role {
    override val key = RoleKeys.NOSTRADAMUS
    override val name = Resources.strings.nostradamus
    override val explanation = Resources.strings.nostradamusExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = true
    override fun getNightAction() = null
}