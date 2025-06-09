package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources


data object Doctor : Role {
    override val key = RoleKeys.DOCTOR
    override val name = Resources.strings.doctor
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data object Detective : Role {
    override val key = RoleKeys.DETECTIVE
    override val name = Resources.strings.detective
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data object Civilian : Role {
    override val key = RoleKeys.CIVILIAN
    override val name = Resources.strings.civilian
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data object Sniper : Role {
    override val key = RoleKeys.SNIPER
    override val name = Resources.strings.sniper
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data object Silencer : Role {
    override val key = RoleKeys.SILENCER
    override val name = Resources.strings.silencer
    override val alignment = Alignment.Mafia
    override fun getNightAction() = null
}

data object Bomber : Role {
    override val key = RoleKeys.BOMBER
    override val name = Resources.strings.bomber
    override val alignment = Alignment.Neutral
    override fun getNightAction() = null
}

data object Bulletproof : Role {
    override val key = RoleKeys.BULLETPROOF
    override val name = Resources.strings.bulletproof
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data object Mayor : Role {
    override val key = RoleKeys.MAYOR
    override val name = Resources.strings.mayor
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data object Oracle : Role {
    override val key = RoleKeys.ORACLE
    override val name = Resources.strings.oracle
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}