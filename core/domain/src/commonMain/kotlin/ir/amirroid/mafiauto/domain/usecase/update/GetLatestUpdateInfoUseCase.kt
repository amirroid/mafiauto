package ir.amirroid.mafiauto.domain.usecase.update

import ir.amirroid.mafiauto.domain.repository.UpdateRepository

class GetLatestUpdateInfoUseCase(
    private val updateRepository: UpdateRepository
) {
    suspend operator fun invoke() = updateRepository.getLatestUpdateInfo()
}