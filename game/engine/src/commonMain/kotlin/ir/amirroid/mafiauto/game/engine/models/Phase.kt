package ir.amirroid.mafiauto.game.engine.models

sealed interface Phase {

    /**
     * Day phase where players discuss and try to identify suspicious behavior.
     */
    data object Day : Phase

    /**
     * Main voting phase where each player casts a vote against a suspect.
     */
    data object Voting : Phase

    /**
     * Players with the highest number of votes defend themselves.
     *
     * @property defenders the list of players required to defend.
     */
    data class Defending(val defenders: List<Player>) : Phase

    /**
     * Phase where a player about to be eliminated is given the chance to play a last-minute card
     * such as "Beautiful Mind", "Handcuff", etc.
     *
     * @property player the player who may play a special card.
     */
    data class LastCard(val player: Player) : Phase

    /**
     * Final random elimination when multiple players tie in votes
     * and no clear decision can be made — one will be eliminated by chance.
     *
     * @property targetPlayer the player selected by random chance.
     */
    data class Fate(val targetPlayer: Player) : Phase

    /**
     * Night phase where special roles perform their actions (e.g. killing, saving, investigating).
     */
    data class Night(val options: List<NightTargetOptions>) : Phase

    /**
     * Result phase where the outcome of the previous night/day is revealed to players.
     */
    data object Result : Phase
}