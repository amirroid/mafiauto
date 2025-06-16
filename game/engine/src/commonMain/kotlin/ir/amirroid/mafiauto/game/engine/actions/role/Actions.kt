package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player

data object KillAction : RoleAction {
    override fun apply(nightAction: NightAction, players: List<Player>, handle: HandleNightAction) {
        val newHealthPoints = nightAction.target.currentHealthPoints - 1
        val isAlive = newHealthPoints != 0
        handle.invoke(
            updatePlayer(
                players, nightAction.target.id
            ) { copy(currentHealthPoints = newHealthPoints, isAlive = isAlive) }
        )
    }
}

data object SaveAction : RoleAction {
    override fun apply(nightAction: NightAction, players: List<Player>, handle: HandleNightAction) {
        if (nightAction.target.canBackWithSave.not() || nightAction.target.isAlive) return
        val newHealthPoints = nightAction.target.currentHealthPoints + 1
        handle.invoke(
            updatePlayer(
                players, nightAction.target.id
            ) { copy(currentHealthPoints = newHealthPoints, isAlive = true) }
        )
    }
}

data object InvestigateAction : RoleAction {
    override fun apply(nightAction: NightAction, players: List<Player>, handle: HandleNightAction) {

    }
}

data object SilentAction : RoleAction {
    override fun apply(nightAction: NightAction, players: List<Player>, handle: HandleNightAction) {

    }
}

data object ConvertAction : RoleAction {
    override fun apply(nightAction: NightAction, players: List<Player>, handle: HandleNightAction) {

    }
}

data object RevealRoleAction : RoleAction {
    override fun apply(nightAction: NightAction, players: List<Player>, handle: HandleNightAction) {

    }
}