package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class HandleFatePhaseUseCase(private val gameRepository: GameRepository) {
    operator fun invoke() = gameRepository.handleFate()
}