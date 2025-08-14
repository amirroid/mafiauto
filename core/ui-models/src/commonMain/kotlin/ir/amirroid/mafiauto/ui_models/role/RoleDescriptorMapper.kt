package ir.amirroid.mafiauto.ui_models.role

import ir.amirroid.mafiauto.domain.model.role.RoleDescriptor
import ir.amirroid.mafiauto.ui_models.getRelatedStringResource


fun RoleDescriptor.toUiModel() = RoleUiModel(
    key = key,
    name = name,
    explanation = explanation,
    formattedAlignment = alignment.getRelatedStringResource(),
    alignment = alignment,
    isOptionalAbility = isOptionalAbility,
    nightActionRequiredPicks = nightActionRequiredPicks,
    instantAction = instantAction,
    maxAbilityUses = maxAbilityUses,
    triggersWhenTargetedBy = triggersWhenTargetedBy,
    executionOrder = executionOrder
)