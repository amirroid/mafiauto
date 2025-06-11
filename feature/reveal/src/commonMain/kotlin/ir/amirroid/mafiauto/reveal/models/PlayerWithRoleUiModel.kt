package ir.amirroid.mafiauto.reveal.models

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
data class PlayerWithRoleUiModel(
    val playerName: String,
    val playerId: Long,
    val roleKey: String,
    val roleName: StringResource,
    val roleExplanation: StringResource,
    val roleAlignment: StringResource
)