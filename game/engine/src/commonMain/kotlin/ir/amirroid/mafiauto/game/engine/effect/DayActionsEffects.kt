package ir.amirroid.mafiauto.game.engine.effect

import ir.amirroid.mafiauto.game.engine.actions.day.GunShotDayAction
import ir.amirroid.mafiauto.game.engine.utils.DayActionNames

class GunShotDayActionEffect(private val isRealGun: Boolean) : DayActionEffect {
    override val name: String = DayActionNames.GUNSHOT
    override fun getAction() = GunShotDayAction(isRealGun = isRealGun)
}