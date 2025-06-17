package ir.amirroid.mafiauto.ui_models.role

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.model.InstantAction
import org.jetbrains.compose.resources.StringResource

@Immutable
data class RoleUiModel(
    val key: String,
    val name: StringResource,
    val explanation: StringResource,
    val formattedAlignment: StringResource,
    val alignment: Alignment,
    val isOptionalAbility: Boolean,
    val instantAction: InstantAction?
)