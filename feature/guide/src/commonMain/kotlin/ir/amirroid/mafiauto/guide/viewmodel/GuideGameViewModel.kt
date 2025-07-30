package ir.amirroid.mafiauto.guide.viewmodel

import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import ir.amirroid.mafiauto.ui_models.role.toUiModel
import kotlinx.collections.immutable.toImmutableList

class GuideGameViewModel(
    getAllRoleDescriptorsUseCase: GetAllRoleDescriptorsUseCase,
) : ViewModel() {
    val roles = getAllRoleDescriptorsUseCase()
        .map { it.toUiModel() }
        .toImmutableList()
}