package ir.amirroid.mafiauto.game.engine.last_card

import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.role.Alignment
import ir.amirroid.mafiauto.game.engine.utils.LastCardKeys
import ir.amirroid.mafiauto.resources.Resources

object BraceletCard : LastCard {
    override val name = Resources.strings.braceletCardName
    override val explanation = Resources.strings.braceletLastCardExplanation
    override val key = LastCardKeys.BRACELET
    override val targetCount = 1
    override fun applyAction(
        player: Player,
        pickedPlayers: List<Player>,
        allPlayers: List<Player>,
        handle: LastCardHandle
    ) {
    }
}

object SilenceCard : LastCard {

    override val name = Resources.strings.silenceCardName
    override val explanation = Resources.strings.silenceLastCardExplanation
    override val key = LastCardKeys.SILENCE
    override val targetCount = 2

    override fun applyAction(
        player: Player,
        pickedPlayers: List<Player>,
        allPlayers: List<Player>,
        handle: LastCardHandle
    ) {
        if (pickedPlayers.isEmpty()) return
        var updatedPlayers = allPlayers
        pickedPlayers.forEach { target ->
            updatedPlayers = updatePlayer(updatedPlayers, target.id) {
                copy(isSilenced = true)
            }
        }
        handle(updatedPlayers)
    }
}

object FaceUpCard : LastCard {
    override val name = Resources.strings.faceUpCardName
    override val explanation = Resources.strings.faceUpLastCardExplanation
    override val key = LastCardKeys.FACE_UP
    override val targetCount = 1
    override fun applyAction(
        player: Player,
        pickedPlayers: List<Player>,
        allPlayers: List<Player>,
        handle: LastCardHandle
    ) {
        val target = pickedPlayers.firstOrNull() ?: return
        val withoutCurrent = updatePlayer(allPlayers, player.id) {
            copy(isAlive = false, canBackWithSave = false)
        }
        val finalPlayers = updatePlayer(withoutCurrent, target.id) {
            copy(role = player.role)
        }
        handle(finalPlayers)
    }
}

object BeautifulMindCard : LastCard {
    override val name = Resources.strings.beautifulMindCardName
    override val explanation = Resources.strings.beautifulMindLastCardExplanation
    override val key = LastCardKeys.BEAUTIFUL_MIND
    override val targetCount = 1
    override fun applyAction(
        player: Player,
        pickedPlayers: List<Player>,
        allPlayers: List<Player>,
        handle: LastCardHandle
    ) {
        val target = pickedPlayers.firstOrNull() ?: return
        if (target.role.alignment == Alignment.Neutral) {
            val newPlayers = updatePlayer(allPlayers, target.id) {
                copy(isAlive = false, canBackWithSave = false)
            }
            handle(newPlayers)
        }
    }
}