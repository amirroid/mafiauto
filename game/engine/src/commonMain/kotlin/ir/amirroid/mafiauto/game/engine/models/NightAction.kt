package ir.amirroid.mafiauto.game.engine.models

data class NightAction(
    val player: Player,
    val targets: List<Player>
)


val NightAction.target: Player
    get() = targets.first()