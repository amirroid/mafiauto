package ir.amirroid.mafiauto.ui_models.role

import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.resources.Resources

fun RoleUiModel.toDomain(): RoleDescriptor {
    return RoleDescriptor(
        name = name,
        explanation = explanation,
        key = key,
        alignment = when (alignment) {
            Resources.strings.mafia -> Alignment.Mafia
            Resources.strings.neutral -> Alignment.Neutral
            else -> Alignment.Civilian
        }
    )
}