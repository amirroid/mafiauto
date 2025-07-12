package ir.amirroid.mafiauto.game.engine.actions.day

import ir.amirroid.mafiauto.game.engine.effect.GunShotDayActionEffect
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.resources.Resources

class GunShotDayAction(private val isRealGun: Boolean) : DayAction {
    override fun applyAction(
        player: Player,
        selectedPlayers: List<Player>,
        allPlayers: List<Player>,
        handler: DayActionHandler
    ) {
        val updatedPlayers = updatePlayer(
            allPlayers, player.id
        ) { copy(effects = effects.filterNot { it is GunShotDayActionEffect }) }
        if (isRealGun) {
            val selectedPlayer = selectedPlayers.firstOrNull() ?: return
            handler.updatePlayers(
                updatePlayer(
                    players = updatedPlayers,
                    targetId = selectedPlayer.id
                ) {
                    copy(
                        isAlive = false,
                        currentHealthPoints = 0,
                    )
                }
            )
        } else {
            handler.updatePlayers(updatedPlayers)
            handler.sendMessage(Resources.strings.bulletBlank)
        }
    }
}