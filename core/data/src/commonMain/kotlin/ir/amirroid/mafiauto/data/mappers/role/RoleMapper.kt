package ir.amirroid.mafiauto.data.mappers.role

import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.model.InstantAction
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.game.engine.role.Alignment as EngineAlignment
import ir.amirroid.mafiauto.game.engine.role.Role

fun Role.toDescriptor(): RoleDescriptor {
    return RoleDescriptor(
        name = name,
        explanation = explanation,
        alignment = alignment.toDomain(),
        key = key,
        hasNightAction = hasNightAction,
        isOptionalAbility = isOptionalAbility,
        instantAction = instantActionType?.let { InstantAction.valueOf(it.name) }
    )
}

private fun EngineAlignment.toDomain(): Alignment {
    return when (this) {
        EngineAlignment.Civilian -> Alignment.Civilian
        EngineAlignment.Neutral -> Alignment.Neutral
        EngineAlignment.Mafia -> Alignment.Mafia
    }
}