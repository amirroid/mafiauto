package ir.amirroid.mafiauto.data.mappers.role

import ir.amirroid.mafiauto.domain.model.role.Alignment
import ir.amirroid.mafiauto.domain.model.game.InstantAction
import ir.amirroid.mafiauto.domain.model.role.RoleDescriptor
import ir.amirroid.mafiauto.game.engine.role.Alignment as EngineAlignment
import ir.amirroid.mafiauto.game.engine.role.Role

fun Role.toDescriptor(): RoleDescriptor {
    return RoleDescriptor(
        name = name,
        explanation = explanation,
        alignment = alignment.toDomain(),
        key = key,
        isOptionalAbility = isOptionalAbility,
        nightActionRequiredPicks = nightActionRequiredPicks,
        instantAction = instantActionType?.let { InstantAction.valueOf(it.name) },
        maxAbilityUses = maxAbilityUses,
        triggersWhenTargetedBy = triggersWhenTargetedBy,
        executionOrder = executionOrder
    )
}

private fun EngineAlignment.toDomain(): Alignment {
    return when (this) {
        EngineAlignment.Civilian -> Alignment.Civilian
        EngineAlignment.Neutral -> Alignment.Neutral
        EngineAlignment.Mafia -> Alignment.Mafia
    }
}