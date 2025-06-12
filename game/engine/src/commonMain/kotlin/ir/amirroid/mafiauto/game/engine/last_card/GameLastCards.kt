package ir.amirroid.mafiauto.game.engine.last_card

import ir.amirroid.mafiauto.game.engine.utils.LastCardKeys
import ir.amirroid.mafiauto.resources.Resources

object BraceletCard : LastCard {
    override val name = Resources.strings.braceletCardName
    override val explanation = Resources.strings.braceletLastCardExplanation
    override val key = LastCardKeys.BRACELET
}

object SilenceCard : LastCard {
    override val name = Resources.strings.silenceCardName
    override val explanation = Resources.strings.silenceLastCardExplanation
    override val key = LastCardKeys.SILENCE
}

object FaceChangeCard : LastCard {
    override val name = Resources.strings.faceUpCardName
    override val explanation = Resources.strings.faceUpLastCardExplanation
    override val key = LastCardKeys.FACE_UP
}

object BeautifulMindCard : LastCard {
    override val name = Resources.strings.beautifulMindCardName
    override val explanation = Resources.strings.beautifulMindLastCardExplanation
    override val key = LastCardKeys.BEAUTIFUL_MIND
}