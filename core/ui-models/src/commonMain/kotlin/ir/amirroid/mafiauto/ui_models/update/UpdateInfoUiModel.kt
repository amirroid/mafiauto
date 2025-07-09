package ir.amirroid.mafiauto.ui_models.update

import androidx.compose.runtime.Immutable

@Immutable
data class UpdateInfoUiModel(
    val needToUpdate: Boolean,
    val release: ReleaseInfo
) {
    @Immutable
    data class ReleaseInfo(
        val name: String,
        val body: String,
        val createdAt: String,
        val tag: String,
        val url: String,
    )
}