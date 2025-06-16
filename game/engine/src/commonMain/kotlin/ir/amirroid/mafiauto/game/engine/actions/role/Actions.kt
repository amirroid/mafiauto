package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.role.Alignment

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

data object SaveAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {
        if (nightAction.target.canBackWithSave.not()) return
        val newHealthPoints = nightAction.target.currentHealthPoints + 1
        handler.updatePlayers(
            updatePlayer(
                players, nightAction.target.id
            ) { copy(currentHealthPoints = newHealthPoints, isAlive = true) }
        )
    }
}

data object InvestigateAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {

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
                    isAlive = currentHealthPoints.minus(1) == 0,
                    currentHealthPoints = currentHealthPoints - 1
                )
            }

            Alignment.Civilian -> updatePlayer(updatedPlayers, shooter.id) {
                copy(isAlive = false)
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

    }
}

data object ConvertAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {

    }
}

data object RevealRoleAction : RoleAction {
    override fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    ) {

    }
}