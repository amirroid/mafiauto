package ir.amirroid.mafiauto.data.mappers.result

import ir.amirroid.mafiauto.domain.model.game.NightActionsResult
import ir.amirroid.mafiauto.game.engine.models.NightActionsResult as EngineNightActionsResult

fun EngineNightActionsResult.toDomain() = NightActionsResult(
    deathCount = deathCount,
    revivedCount = revivedCount
)