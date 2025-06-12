package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class GetCurrentPhaseUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke() = gameRepository.currentPhase
}