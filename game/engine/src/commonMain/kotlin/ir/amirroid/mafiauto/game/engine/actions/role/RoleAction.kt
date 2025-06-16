package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player
import kotlinx.coroutines.flow.update

typealias HandleNightAction = (List<Player>?) -> Unit

interface RoleAction {
    val delayInDays: Int get() = 0
    fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handle: HandleNightAction
    )

    fun updatePlayer(
        players: List<Player>,
        targetId: Long,
        transform: Player.() -> Player
    ): MutableList<Player> {
        return players.toMutableList().apply {
            val index = indexOfFirst { player -> player.id == targetId }
            if (index != -1) {
                this[index] = this[index].transform()
            }
        }
    }
}