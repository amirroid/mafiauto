package ir.amirroid.mafiauto.game.engine.provider.last_card

import ir.amirroid.mafiauto.game.engine.last_card.LastCard

interface LastCardsProvider {
    fun getAllLastCards(): List<LastCard>
}