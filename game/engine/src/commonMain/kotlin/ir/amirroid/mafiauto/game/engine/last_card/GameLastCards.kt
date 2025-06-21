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
        handler: LastCardHandler
    ) {
        val target = pickedPlayers.firstOrNull() ?: return
        handler.sendMessage(Resources.strings.doneMessage)
        val withoutCurrent = updatePlayer(allPlayers, player.id) {
            copy(isAlive = false)
        }
        handler.updatePlayers(
            updatePlayer(
                players = withoutCurrent,
                target.id
            ) { copy(canUseAbility = false) }
        )
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
        handler: LastCardHandler
    ) {
        if (pickedPlayers.isEmpty()) return
        var updatedPlayers = updatePlayer(allPlayers, player.id) {
            copy(isAlive = false)
        }
        pickedPlayers.forEach { target ->
            updatedPlayers = updatePlayer(updatedPlayers, target.id) {
                copy(isSilenced = true)
            }
        }
        handler.sendMessage(Resources.strings.doneMessage)
        handler.updatePlayers(updatedPlayers)
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
        handler: LastCardHandler
    ) {
        val target = pickedPlayers.firstOrNull() ?: return
        val withoutCurrent = updatePlayer(allPlayers, player.id) {
            copy(isAlive = false, canBackWithSave = false, role = target.role)
        }
        val finalPlayers = updatePlayer(withoutCurrent, target.id) {
            copy(role = player.role)
        }
        handler.sendMessage(Resources.strings.doneMessage)
        handler.updatePlayers(finalPlayers)
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
        handler: LastCardHandler
    ) {
        val target = pickedPlayers.firstOrNull() ?: return
        if (target.role.alignment == Alignment.Neutral) {
            val newPlayers = updatePlayer(allPlayers, target.id) {
                copy(isAlive = false, canBackWithSave = false)
            }
            handler.sendMessage(Resources.strings.correctGuess)
            handler.updatePlayers(newPlayers)
        } else {
            handler.sendMessage(Resources.strings.wrongGuess)
            handler.updatePlayers(
                updatePlayer(allPlayers, player.id) {
                    copy(isAlive = false)
                }
            )
        }
    }
}