package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player

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