package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.*
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys


data class Doctor(val targetId: Int?) : Role {
    override val key = RoleKeys.DOCTOR
    override val englishName = "Doctor"
    override val persianName = "دکتر"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = targetId?.let { SaveAction(key, it) }
}

data class Detective(val targetId: Int?) : Role {
    override val key = RoleKeys.DETECTIVE
    override val englishName = "Detective"
    override val persianName = "کارآگاه"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = targetId?.let { InvestigateAction(key, it) }
}

class Civilian : Role {
    override val key = RoleKeys.CIVILIAN
    override val englishName = "Civilian"
    override val persianName = "شهروند"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data class Sniper(val targetId: Int?) : Role {
    override val key = RoleKeys.SNIPER
    override val englishName = "Sniper"
    override val persianName = "تک‌تیرانداز"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = targetId?.let { KillAction(key, it, delayInDays = 2) }
}

data class Silencer(val targetId: Int?) : Role {
    override val key = RoleKeys.SILENCER
    override val englishName = "Silencer"
    override val persianName = "ساکت‌کننده"
    override val alignment = Alignment.Mafia
    override fun getNightAction() = targetId?.let { SilentAction(key, it) }
}

data class Bomber(val victimId: Int?) : Role {
    override val key = RoleKeys.BOMBER
    override val englishName = "Bomber"
    override val persianName = "بمب‌گذار"
    override val alignment = Alignment.Neutral
    override fun getNightAction() = null
}

class Bulletproof : Role {
    override val key = RoleKeys.BULLETPROOF
    override val englishName = "Bulletproof"
    override val persianName = "ضد گلوله"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data class Mayor(val targetId: Int?) : Role {
    override val key = RoleKeys.MAYOR
    override val englishName = "Mayor"
    override val persianName = "شهردار"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
}

data class Oracle(val targetId: Int?) : Role {
    override val key = RoleKeys.ORACLE
    override val englishName = "Oracle"
    override val persianName = "پیشگو"
    override val alignment = Alignment.Civilian
    override fun getNightAction() = targetId?.let { RevealRoleAction(key, it) }
}