package ir.amirroid.mafiauto.ui_models.role

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
data class RoleUiModel(
    val key: String,
    val name: StringResource,
    val explanation: StringResource,
    val alignment: StringResource
)
