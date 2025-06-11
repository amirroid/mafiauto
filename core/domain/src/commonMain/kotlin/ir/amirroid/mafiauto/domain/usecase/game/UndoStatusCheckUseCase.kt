package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class UndoStatusCheckUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke() = gameRepository.undoStatusCheck()
}