package ir.amirroid.mafiauto.game.engine.provider.last_card

import ir.amirroid.mafiauto.game.engine.last_card.*

class LastCardsProviderImpl : LastCardsProvider {
    override fun getAllLastCards(): List<LastCard> {
        return listOf(
            BraceletCard,
            SilenceCard,
            FaceUpCard,
            BeautifulMindCard,
            AuthenticationCard
        )
    }
}