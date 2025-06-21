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
    override val hasNightAction = true
    override fun getNightAction() = KillAction
    override val healthPoints: Int = 2

    override fun getNightActionTargetPlayers(
        previewsTarget: Player?,
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
    override val hasNightAction = false
    override fun getNightAction() = null
}

data object Surgeon : Role {
    override val key = RoleKeys.SURGEON
    override val name = Resources.strings.surgeon
    override val explanation = Resources.strings.surgeonExplanation
    override val alignment = Alignment.Mafia
    override val hasNightAction = true

    override fun getNightAction(): RoleAction = SurgeonSaveAction
    override fun getNightActionTargetPlayers(
        previewsTarget: Player?,
        allPlayers: List<Player>
    ): List<Player> {
        return allPlayers.filter { it.canBackWithSave && it.isInGame && it.role.alignment == Alignment.Mafia && it.role.key != RoleKeys.GOD_FATHER }
    }
}
