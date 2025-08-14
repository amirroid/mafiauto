package ir.amirroid.mafiauto.guide.viewmodel

import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import ir.amirroid.mafiauto.ui_models.role.toUiModel
import kotlinx.collections.immutable.toImmutableList

class GameGuideViewModel(
    getAllRoleDescriptorsUseCase: GetAllRoleDescriptorsUseCase,
) : ViewModel() {
    val roles = getAllRoleDescriptorsUseCase()
        // Remove digits from key to treat role variants as the same
        .distinctBy { it.key.filter { ch -> !ch.isDigit() } }
        .map { it.toUiModel() }
        .toImmutableList()
}