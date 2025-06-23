package ir.amirroid.mafiauto.game.engine.models

import ir.amirroid.mafiauto.game.engine.role.Alignment

sealed interface Phase {

    /**
     * Phase where players discuss freely to identify suspicious behavior
     * and try to persuade others before any voting begins.
     */
    data object Day : Phase

    /**
     * Phase where all players vote publicly to accuse a suspect of being evil.
     */
    data object Voting : Phase

    /**
     * Phase where the most-voted players are given a chance to defend themselves
     * before a final vote or decision is made.
     *
     * @property defenders The list of players who must defend themselves.
     */
    data class Defending(val defenders: List<Player>) : Phase

    /**
     * Phase where a player at risk of elimination is given a chance
     * to use a last-minute ability or card (e.g., "Beautiful Mind", "Handcuff").
     *
     * @property player The player who may play a special ability or card.
     */
    data class LastCard(val player: Player) : Phase

    /**
     * Phase used when multiple players tie in the vote count
     * and no clear elimination can be determined. One player is chosen at random.
     *
     * @property targetPlayer The player selected by chance for elimination.
     * @property sameVotesDefenders The players who were tied in the vote.
     */
    data class Fate(val targetPlayer: Player, val sameVotesDefenders: List<Player>) : Phase

    /**
     * Phase that occurs at night when special roles (e.g., Mafia, Doctor, Investigator)
     * secretly perform their actions.
     *
     * @property options A list of possible targets for night actions.
     */
    data class Night(val options: List<NightTargetOptions>) : Phase

    /**
     * Phase where the outcome of night actions (e.g., deaths, revivals, investigations)
     * is revealed to all players before transitioning back to the Day phase.
     *
     * @property result The result of all night actions, including deaths and revivals.
     */
    data class Result(val result: NightActionsResult) : Phase


    /**
     * Final phase of the game that indicates it has ended.
     *
     * @property winnerAlignment The alignment (e.g., Mafia, Civilian) that has won the game.
     */
    data class End(val winnerAlignment: Alignment) : Phase
}