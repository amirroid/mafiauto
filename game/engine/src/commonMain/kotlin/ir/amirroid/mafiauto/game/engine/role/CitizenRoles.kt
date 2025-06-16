package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.SaveAction
import ir.amirroid.mafiauto.game.engine.actions.role.ShootAction
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources


data object Doctor : Role {
    override val key = RoleKeys.DOCTOR
    override val name = Resources.strings.doctor
    override val explanation = Resources.strings.doctorExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = true
    override fun getNightAction() = SaveAction
    override val executionOrder: Int = 3
    override fun getNightActionTargetPlayers(
        previewsTarget: Player?,
        allPlayers: List<Player>
    ): List<Player> {
        return (if (previewsTarget?.role?.key == RoleKeys.DOCTOR) {
            allPlayers.filter { player -> player != previewsTarget }
        } else allPlayers).filter { it.isInGame && it.canBackWithSave }
    }
}

data object Detective : Role {
    override val key = RoleKeys.DETECTIVE
    override val name = Resources.strings.detective
    override val explanation = Resources.strings.detectiveExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = true
    override val executionOrder: Int = 3
    override fun getNightAction() = null
}

data object Civilian : Role {
    override val key = RoleKeys.CIVILIAN
    override val name = Resources.strings.civilian
    override val explanation = Resources.strings.civilianExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = false
    override val executionOrder: Int = 3
    override fun getNightAction() = null
}

data object Sniper : Role {
    override val key = RoleKeys.SNIPER
    override val name = Resources.strings.sniper
    override val explanation = Resources.strings.sniperExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = true
    override val executionOrder: Int = 3
    override val isOptionalAbility: Boolean = true
    override val maxAbilityUses: Int = 2
    override fun getNightAction() = ShootAction
}

data object Bomber : Role {
    override val key = RoleKeys.BOMBER
    override val name = Resources.strings.bomber
    override val explanation = Resources.strings.bomberExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = true
    override val executionOrder: Int = 3
    override fun getNightAction() = null
}

data object Bulletproof : Role {
    override val key = RoleKeys.BULLETPROOF
    override val name = Resources.strings.bulletproof
    override val explanation = Resources.strings.bulletproofExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = true
    override val executionOrder: Int = 3
    override fun getNightAction() = null
}

data object Mayor : Role {
    override val key = RoleKeys.MAYOR
    override val name = Resources.strings.mayor
    override val explanation = Resources.strings.mayorExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = true
    override val executionOrder: Int = 3
    override fun getNightAction() = null
}

data object Oracle : Role {
    override val key = RoleKeys.ORACLE
    override val name = Resources.strings.oracle
    override val explanation = Resources.strings.oracleExplanation
    override val alignment = Alignment.Civilian
    override val hasNightAction = true
    override val executionOrder: Int = 3
    override fun getNightAction() = null
}