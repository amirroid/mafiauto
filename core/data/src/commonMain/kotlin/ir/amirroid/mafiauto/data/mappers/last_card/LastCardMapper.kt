package ir.amirroid.mafiauto.data.mappers.last_card

import ir.amirroid.mafiauto.domain.model.game.LastCardDescriptor
import ir.amirroid.mafiauto.game.engine.last_card.LastCard

fun LastCard.toDomain() = LastCardDescriptor(
    name = name,
    explanation = explanation,
    key = key,
    targetCount = targetCount
)