package ir.amirroid.mafiauto.domain.model

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
data class RoleDescriptor(
    val name: StringResource,
    val key: String
)