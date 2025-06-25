package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.game.engine.actions.role.*
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.resources.Resources

data object GodFather : Role {
    override val key = RoleKeys.GOD_FATHER
    override val name = Resources.strings.godFather
    override val explanation = Resources.strings.godFatherExplanation
    override val alignment = Alignment.Mafia
    override fun getNightAction() = KillAction
    override val healthPoints: Int = 2

    override fun getNightActionTargetPlayers(
        previewsTargets: List<Player>?,
        allPlayers: List<Player>
    ): List<Player> {
        return allPlayers.filter { it.role.key != RoleKeys.GOD_FATHER && it.isInGame }
    }
}

data object Mafia : Role {
    override val key = RoleKeys.MAFIA
    override val name = Resources.strings.mafia
    override val explanation = Resources.strings.mafiaExplanation
    override val alignment = Alignment.Mafia
    override fun hasNightAction(players: List<Player>): Boolean = false
    override fun getNightAction() = null
}

data object Surgeon : Role {
    override val key = RoleKeys.SURGEON
    override val name = Resources.strings.surgeon
    override val explanation = Resources.strings.surgeonExplanation
    override val alignment = Alignment.Mafia

    override fun getNightAction(): RoleAction = SurgeonSaveAction
    override fun getNightActionTargetPlayers(
        previewsTargets: List<Player>?,
        allPlayers: List<Player>
    ): List<Player> {
        return allPlayers.filter { it.canBackWithSave && it.isInGame && it.role.alignment == Alignment.Mafia && it.role.key != RoleKeys.GOD_FATHER }
    }
}


data object SaulGoodman : Role {
    override val key = RoleKeys.SAUL_GOODMAN
    override val name = Resources.strings.saulGoodman
    override val explanation = Resources.strings.saulGoodmanExplanation
    override val alignment = Alignment.Mafia
    override val maxAbilityUses: Int = 1
    override val isOptionalAbility: Boolean = true

    override fun hasNightAction(players: List<Player>): Boolean {
        return players.count { it.role.alignment == Alignment.Mafia && !it.isInGame } > 0
    }

    override fun getNightAction(): RoleAction = BuyMafiaAction
    override fun getNightActionTargetPlayers(
        previewsTargets: List<Player>?,
        allPlayers: List<Player>
    ): List<Player> {
        return allPlayers.filter { it.isInGame && it.role.alignment != Alignment.Mafia }
    }
}
