package ir.amirroid.mafiauto.game.engine.actions.role

import co.touchlab.kermit.Logger
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.models.target
import ir.amirroid.mafiauto.game.engine.role.Alignment
import ir.amirroid.mafiauto.game.engine.role.Mafia
import ir.amirroid.mafiauto.game.engine.utils.RegexUtils
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources

data object KillAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        val newHealthPoints = nightAction.target.currentHealthPoints - 1
        val isAlive = newHealthPoints != 0
        handler.updatePlayers(
            updatePlayer(
                players, nightAction.target.id
            ) { copy(currentHealthPoints = newHealthPoints, isAlive = isAlive) }
        )
    }
}

data object DoctorSaveAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        if (nightAction.target.canBackWithSave.not() || nightAction.target.role.alignment == Alignment.Mafia) return
        val newHealthPoints = nightAction.target.currentHealthPoints + 1
        handler.updatePlayers(
            updatePlayer(
                players, nightAction.target.id
            ) {
                copy(
                    currentHealthPoints = if (isAlive) currentHealthPoints else newHealthPoints,
                    isAlive = true
                )
            }
        )
    }
}

data object SurgeonSaveAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        if (nightAction.target.canBackWithSave.not() || nightAction.target.role.alignment != Alignment.Mafia) return
        val newHealthPoints = nightAction.target.currentHealthPoints + 1
        handler.updatePlayers(
            updatePlayer(
                players, nightAction.target.id
            ) {
                copy(
                    currentHealthPoints = if (isAlive) currentHealthPoints else newHealthPoints,
                    isAlive = true
                )
            }
        )
    }
}

data object ShootAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        if (nightAction.player.remainingAbilityUses == 0) return

        val shooter = nightAction.player
        val target = nightAction.target
        val updatedPlayers = updatePlayer(players, shooter.id) {
            copy(remainingAbilityUses = remainingAbilityUses - 1)
        }

        val finalPlayers = when (target.role.alignment) {
            Alignment.Mafia -> updatePlayer(updatedPlayers, target.id) {
                copy(
                    isAlive = currentHealthPoints.minus(1) != 0,
                    currentHealthPoints = currentHealthPoints - 1
                )
            }

            Alignment.Civilian -> updatePlayer(updatedPlayers, shooter.id) {
                copy(isAlive = false, currentHealthPoints = 1)
            }

            Alignment.Neutral -> updatedPlayers
        }

        handler.updatePlayers(finalPlayers)
    }
}

data object SilentAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        if (nightAction.player.remainingAbilityUses == 0) return
        val updatedPlayers = updatePlayer(players, nightAction.player.id) {
            copy(remainingAbilityUses = remainingAbilityUses - 1)
        }
        handler.updatePlayers(
            updatePlayer(
                updatedPlayers,
                nightAction.target.id
            ) { copy(isSilenced = true) }
        )
    }
}


data object BuyMafiaAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        val target = nightAction.target
        val updatedPlayers = updatePlayer(players, nightAction.player.id) {
            copy(remainingAbilityUses = remainingAbilityUses - 1)
        }
        if (Regex(RegexUtils.CIVILIAN_REGEX).matches(target.role.key)) {
            handler.updatePlayers(
                updatePlayer(updatedPlayers, target.id) {
                    copy(role = Mafia)
                }
            )
        } else {
            handler.updatePlayers(updatedPlayers)
        }
    }
}