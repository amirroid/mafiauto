package ir.amirroid.mafiauto.ui_models.role

import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.resources.Resources


fun RoleDescriptor.toUiModel() = RoleUiModel(
    key = key,
    name = name,
    explanation = explanation,
    formattedAlignment = when (alignment) {
        Alignment.Mafia -> Resources.strings.mafia
        Alignment.Civilian -> Resources.strings.civilian
        Alignment.Neutral -> Resources.strings.neutral
    },
    alignment = alignment,
    isOptionalAbility = isOptionalAbility,
    nightActionRequiredPicks = nightActionRequiredPicks,
    instantAction = instantAction
)