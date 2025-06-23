package ir.amirroid.mafiauto.game.engine.models

import ir.amirroid.mafiauto.game.engine.role.Role

data class NightAction(
    val player: Player,
    val targets: List<Player>,
    val replacementRole: Role?
)


val NightAction.target: Player
    get() = targets.first()